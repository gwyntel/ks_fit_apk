"""Walking Pad MC11 Api."""
import asyncio
import logging
import time

from bleak.backends.device import BLEDevice

from .controller_mc11 import ControllerMc11, WalkingPadCurStatus

_LOGGER = logging.getLogger(__name__)

STATUS_LOCK_ON_CMD_SECONDS = 5


class WalkingPadApiMc11:
    """Walkingpad MC11 device."""

    def __init__(self, name: str, ble_device: BLEDevice) -> None:
        """Create a new walking pad api instance."""
        self._name = name
        self._ble_device = ble_device
        self._ctrl = ControllerMc11()
        self._callbacks = []
        self._status_lock = False
        self._last_cmd_time = time.time()

        self._connected = False
        self._moving = False
        self._speed = 0
        self._distance = 0
        self._time = 0
        self._steps = 0
        self._step_cadence = 0

        self._last_status = None
        self._last_status_time = 0.0
        self._session_start_time = 0.0

        self._register_controller_callbacks()

    def _register_controller_callbacks(self):
        self._ctrl.handler_cur_status = self._on_status_update

    def _begin_cmd(self) -> asyncio.Lock:
        self._status_lock = True
        return asyncio.Lock()

    async def _end_cmd(self):
        await asyncio.sleep(0.75)
        self._last_cmd_time = time.time()
        self._status_lock = False

    def _on_status_update(self, sender, status: WalkingPadCurStatus) -> None:
        """Update current state."""
        # Don't update if we're still running a command or just did (status from device is outdated at first)
        if (
            self._status_lock
            or time.time() - self._last_cmd_time < STATUS_LOCK_ON_CMD_SECONDS
        ):
            return

        self._moving = status.speed > 0
        if self._moving and self._session_start_time == 0.0:
            self._session_start_time = time.time()
        elif not self._moving:
            self._session_start_time = 0.0

        self._speed = status.speed
        self._distance = status.dist
        if self._session_start_time > 0:
            self._time = int(time.time() - self._session_start_time)
        else:
            self._time = 0
        self._steps = status.steps

        # only try to calculate cadence if we have all needed infos
        if self._moving and self._last_status is not None and self._last_status_time > 0 and self._last_status.speed > 0 and self._last_status.time != status.time:
            # calculate steps per minute
            time_diff = time.time() - self._last_status_time
            if time_diff > 0:
                self._step_cadence = round((status.steps - self._last_status.steps) / (time_diff / 60.0), 2)
                if self._step_cadence < 0:
                    self._step_cadence = 0
        else:
            self._step_cadence = 0

        # store last status msg
        self._last_status_time = time.time()
        self._last_status = status

        if len(self._callbacks) > 0:
            for callback in self._callbacks:
                callback(status)

    def register_status_callback(self, callback) -> None:
        """Register a status callback."""
        self._callbacks.append(callback)

    @property
    def mac(self):
        """Mac address."""
        return self._ble_device.address

    @property
    def name(self):
        """Name."""
        return self._name

    @property
    def connected(self):
        """Connected status."""
        return self._connected

    @property
    def moving(self):
        """Whether or not the device is currently moving."""
        return self._moving

    @property
    def speed(self):
        """The current device speed."""
        return self._speed

    @property
    def distance(self):
        """The current device distance."""
        return self._distance

    @property
    def time(self):
        """The duration of the current session."""
        return self._time

    @property
    def steps(self):
        """The number of steps taken in the current session."""
        return self._steps

    @property
    def step_cadence(self):
        """The steps cadence in steps per minute based on the current and last value."""
        return self._step_cadence

    async def connect(self) -> None:
        """Connect the device."""
        lock = self._begin_cmd()
        async with lock:
            await self._ctrl.run(self._ble_device)
            self._connected = True
            await self._end_cmd()

    async def disconnect(self) -> None:
        """Disconnect the device."""
        lock = self._begin_cmd()
        async with lock:
            await self._ctrl.disconnect()
            self._connected = False
            await self._end_cmd()

    async def turn_on(self) -> None:
        """Turn on the device."""
        pass

    async def turn_off(self) -> None:
        """Turn off the device."""
        pass

    async def start_belt(self) -> None:
        """Start the belt."""
        if self.connected is not True:
            return

        lock = self._begin_cmd()
        async with lock:
            await self._ctrl.start_belt()
            self._moving = True
            self._session_start_time = time.time()
            await self._end_cmd()

    async def stop_belt(self) -> None:
        """Stop the belt."""
        if self.connected is not True:
            return

        lock = self._begin_cmd()
        async with lock:
            await self._ctrl.stop_belt()
            self._moving = False
            self._session_start_time = 0.0
            await self._end_cmd()

    async def change_speed(self, speed: int) -> None:
        """Change the speed."""

        if self.connected is not True:
            return
        lock = self._begin_cmd()
        async with lock:
            await self._ctrl.change_speed(speed)
            self._speed = speed
            await self._end_cmd()

    async def update_state(self) -> None:
        """Update device state."""
        if self.connected is not True:
            try:
                await self.connect()
            except Exception as e:
                _LOGGER.warn("failed to connect to Walking Pad: %s", e)
            return
