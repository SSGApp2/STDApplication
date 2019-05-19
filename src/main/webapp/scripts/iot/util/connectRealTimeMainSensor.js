'use strict';


var stompClient = null;
var username = null;

var socketSensor = {
    deviceCode: ''
};


var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

const URL_SOCKET = ServerConstant['VCCWebSocketServer'];
var CURRENT_MAIN_SENSOR = {};
$(function () {
    connect();
});

var SOCKET_DISPLAY_STATUS;

function connect() {
    var socket = new SockJS(URL_SOCKET + '/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    stompClient.debug = null;
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    SOCKET_DISPLAY_STATUS="Connect";
    // Tell your username to the server
    // stompClient.send("/app/chat.addUser",
    //     {},
    //     JSON.stringify({sender: username, type: 'JOIN'})
    // )
}

function onError(error) {
    SOCKET_DISPLAY_STATUS="Disconnect";
    console.log('==============================================================================');
    console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
    setTimeout(function () {
        //Reconnect
        if(SOCKET_DISPLAY_STATUS=="Disconnect"){
            connect();
        }
    },5000);
    console.log('==============================================================================');
}


function sendMessage() {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var mainSensor = JSON.parse(payload.body);
    if (socketSensor['deviceCode']
        && socketSensor['deviceCode'] == mainSensor['deviceName']
        && session.ouCode == mainSensor['ouCode']) {
        CURRENT_MAIN_SENSOR = mainSensor;
        console.log('Fond Data')
    }
    // console.log(CURRENT_MAIN_SENSOR);
}


socketSensor.setDeviceCode = function (deviceCode) {
    socketSensor['deviceCode'] = deviceCode;
}

socketSensor.setCurrentData=function () {
    return CURRENT_MAIN_SENSOR;
}