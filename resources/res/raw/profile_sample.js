/*ICA协议标准方法*/
var ALINK_VERSION = '1.0';
var METHOD_GET_STR = 'thing.service.property.get';
var METHOD_SET_STR = 'thing.service.property.set';
var METHOD_SERVICE_STR_PREFIX = 'thing.service.';
var METHOD_REPORT_STR = 'thing.event.property.post';
var METHOD_EVENT_STR_PREFIX = 'thing.event.';
var METHOD_ACK_STR  = 'ACK';

/*
ICA消息格式定义
//##get property消息
{"id":123,"version":"1.0","method":"thing.service.property.get","params":["KEY1","KEY2"]}

//##set property消息
{"id":123,"version":"1.0","method":"thing.service.property.set","params":{"KEY1":"VALUE1"}}

//##call service消息
{"id":123,"version":"1.0","method":"thing.service.$(SERVICENAME)","params":{"KEY1":"VALUE1"}}

//##call service reply消息（非必须）
{"id":123,"method":"thing.service.$(SERVICENAME)","code":200,"data":{"KEY1":"VALUE1"}}

//##post/get_reply property消息
{"id":123,"version":"1.0","method":"thing.event.property.post","params":{"KEY1":"VALUE1"}}

//##event消息
{"id":123,"version":"1.0","method":"thing.event.$(EVENTNAME)","params":{"KEY1":"VALUE1"}}
*/




/*
 *设备私有协议命令字，开发者自定义
*/
var VENDOR_METHOD_GET_CMD = 0x00;
var VENDOR_METHOD_SET_CMD = 0x01;
var VENDOR_METHOD_SERVICE_CMD = 0x02;
var VENDOR_METHOD_SERVICE_REPLY_CMD = 0x04;
var VENDOR_METHOD_ACK_CMD = 0x08;
var VENDOR_METHOD_REPORT_CMD = 0x80;
var VENDOR_METHOD_EVENT_CMD = 0x81;



function protocolToRawData(json)
{
	log('------------------------ protocolToRawData start ------------------------');
    log('ica json: ' + JSON.stringify(json));
	var params = json['params'];
	var method = json['method'];
	var id = json['id'];
	var payloadArray = [];


    /*-------------------------------------ica协议转厂商私有协议代码sample start---------------------------------------*/
    var length = 0;
    var cmd = 0;
	var jsonMap = new Object();
    length = JSON.stringify(params).length;
    log('params length: ' + length);

    jsonMap['params'] = params;
	if (method == METHOD_GET_STR)
	{
        cmd = VENDOR_METHOD_GET_CMD;
	}
	else if (method == METHOD_SET_STR)
	{
        cmd = VENDOR_METHOD_SET_CMD;
	}
	else
	{
        cmd = VENDOR_METHOD_SERVICE_CMD;
        var serviceName = method.substring(METHOD_SERVICE_STR_PREFIX.length);
        log('serviceName: ' + serviceName);
        if (serviceName == '') {
            log('invalid method: ' + method);
            return;
        }

        jsonMap['serviceName'] = serviceName;
        var payload = {"serviceName":serviceName, "params":params};
        length = JSON.stringify(payload).length;
	}

    var paramsStr = JSON.stringify(jsonMap);
    log('paramsBytes length: ' + paramsStr.length + " pamrams: " + paramsStr);

    var retBuffer = new Uint8Array(1 + 4 + 4 + paramsStr.length);  //head length ver cmd payload checksum
    var dv = new DataView(retBuffer.buffer, 0);
    retBuffer[0] = cmd; //head
    dv.setUint32(1, id); // length -> ver cmd payload checksum
    dv.setUint32(5, paramsStr.length); // length -> ver cmd payload checksum

    for (var i = 0; i < paramsStr.length; i++)
    {
        retBuffer[i + 9] = paramsStr.charCodeAt(i);
    }
    /*-------------------------------------ica协议转厂商私有协议代码sample end---------------------------------------*/


    payloadArray = payloadArray.concat([].slice.call(retBuffer));
    log("rawdata bytes: " + payloadArray.toString());
	log('------------------------ protocolToRawData end ------------------------');

    return payloadArray;
}

function rawDataToProtocol(bytes)
{
    log('------------------------ rawDataToProtocol start ------------------------');
    log('bytes length: ' + bytes.length);


    var buffer = new Uint8Array(bytes.length);
    log("buffer length: " + buffer.length);
    var i = 0;
    for (i = 0; i < bytes.length; i++) {
        buffer[i] = bytes[i];
    }


    /*-------------------------------------厂商私有协议转ica协议代码sample start---------------------------------------*/
    var dv = new DataView(buffer.buffer, 0);
    var cmd = buffer[0];
    var id = dv.getUint32(1);
    //var length = dv.getUint32(5);
    //head length 9
    var length = bytes.length - 9;
    log('cmd: ' + cmd + ' id: ' + id + ' length: ' + length);

    var paramsStr = "";
    for (i = 0; i < length; i++) {
        paramsStr += String.fromCharCode(bytes[i + 9]);
    }
    log('paramsString: ' + paramsStr);

    var bodyObj = JSON.parse(paramsStr);
	//decode payload
	var jsonMap = new Object();
	if (cmd  == VENDOR_METHOD_ACK_CMD) //get/set属性确认消息
	{
		jsonMap['method'] = METHOD_ACK_STR;
		var data = bodyObj['data'];
		if (undefined == data)
			jsonMap['data'] = JSON.parse('{}');
		else
			jsonMap['data'] = data;

		jsonMap['code'] = 200;
	}
	else if (cmd == VENDOR_METHOD_GET_CMD) //get响应消息用report方法上报
	{
		jsonMap['method'] = METHOD_REPORT_STR;
		jsonMap['params'] = bodyObj['data'];
	}
	else if (cmd == VENDOR_METHOD_REPORT_CMD)  //report属性消息
	{
		jsonMap['method'] = METHOD_REPORT_STR;
		jsonMap['params'] = bodyObj['params'];
	}
    else if (cmd == VENDOR_METHOD_EVENT_CMD)  //report事件消息
    {
        var event = bodyObj['eventName'];
        var method = METHOD_EVENT_STR_PREFIX + event;
        jsonMap['method'] = method;
        jsonMap['params'] = bodyObj['params'];
    }
	else if (cmd == VENDOR_METHOD_SERVICE_REPLY_CMD)//service reply消息上报格式
	{
        var serviceName = bodyObj['serviceName'];
        var method = METHOD_SERVICE_STR_PREFIX + serviceName;
		jsonMap['method'] = method;
		jsonMap['code'] = 200;
		jsonMap['data'] = bodyObj['data'];
	}
	else
	{
		//do nothing
        log("ignore cmd: " + cmd);
		return;
	}
    /*-------------------------------------厂商私有协议转ica协议代码sample end---------------------------------------*/

	
    jsonMap['id'] = '' + id;
	jsonMap['version'] = ALINK_VERSION;

	log("finished, ica msg: " + JSON.stringify(jsonMap));
	log('------------------------ rawDataToProtocol end ------------------------');
  	return jsonMap;
}


/*log打印函数*/
function log(){
    try{
        console.log.apply(console, arguments);
    } catch (e){

    }
    try{
        print.apply(print, arguments);
    } catch (e){

    }

    try{
            print(arguments);
        } catch (e){

    }
};


