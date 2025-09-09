import asyncio
import logging

from bleak import BleakClient

_LOGGER = logging.getLogger(__name__)

SERVICE_UUID = "000018ee-0000-1000-8000-00805f9b34fb"
WRITE_UUID = "00002b11-0000-1000-8000-00805f9b34fb"
READ_UUID = "00002b11-0000-1000-8000-00805f9b34fb"

CMD_START = b'\xfd\x11\x01\x01'
CMD_STOP = b'\xfd\x00\x01\x01'
CMD_SPEED_HEADER = b'\x3f\x06\x01'


class WalkingPadCurStatus:
    """Current status of the walking pad."""

    def __init__(self, data):
        """Initialize."""
        self.speed = int.from_bytes(data[5:7], byteorder='big') / 100
        self.steps = int.from_bytes(data[7:11], byteorder='big')
        self.dist = int.from_bytes(data[11:13], byteorder='big')
        self.time = 0 # This will be calculated in the API class


class ControllerMc11:
    """Controller for the MC11."""

    def __init__(self):
        """Initialize."""
        self.handler_cur_status = None
        self.client = None
        self.device = None

    async def run(self, device):
        """Run the controller."""
        self.device = device
        self.client = BleakClient(device)
        await self.client.connect()
        await self.client.start_notify(READ_UUID, self.handle_notify)

    async def disconnect(self):
        """Disconnect."""
        if self.client:
            await self.client.disconnect()

    def handle_notify(self, sender, data):
        """Handle notification."""
        _LOGGER.debug("Received data: %s", data.hex())
        if data and len(data) > 1 and data[0] == 0xfd and data[1] == 0x11:
            status = WalkingPadCurStatus(data)
            if self.handler_cur_status:
                self.handler_cur_status(sender, status)

    async def send_cmd(self, cmd):
        """Send command."""
        if self.client:
            await self.client.write_gatt_char(WRITE_UUID, cmd, response=True)

    async def start_belt(self):
        """Start the belt."""
        await self.send_cmd(CMD_START)

    async def stop_belt(self):
        """Stop the belt."""
        await self.send_cmd(CMD_STOP)

    async def change_speed(self, speed):
        """Change the speed."""
        speed_val = int(speed * 100)
        speed_bytes = speed_val.to_bytes(2, byteorder='big')
        await self.send_cmd(CMD_SPEED_HEADER + speed_bytes)
