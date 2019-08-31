class WebSocketClient {

    constructor(protocol, hostname, port, endpoint) {

        this.webSocket = null;

        this.protocol = protocol;
        this.hostname = hostname;
        this.port = port;
        this.endpoint = endpoint;
    }

    getServerUrl() {
        return this.protocol + "://" + this.hostname + ":" + this.port + this.endpoint;
    }

   

    connect() {
        try {
            this.webSocket = new WebSocket(this.getServerUrl());
            this.webSocket.onopen = function(event) {
                console.log('onopen::' + JSON.stringify(event, null, 4));
            }

            this.webSocket.onmessage = function(event) {
                var msg = event.data;

              	var user = JSON.parse(msg).user;
        		console.log(msg);
		        var data = '<div class="notification is-success"><button class="delete"></button><h4>HNI Alert </h4> <h5> Name : ' + user + '</h5><h5><a href="/Face/admin/userdata?user='+user+'">Click here to see more details</a></h5></div>';
		        $(".notify").append(data);
		        $(".delete").click(function() {
		            $(this).parent().fadeOut(500);
		            setTimeout(function() {
		                $(this).parent().remove();
		            }, 500);
		
		        });

                
            }
            this.webSocket.onclose = function(event) {
                console.log('onclose');
            }
            this.webSocket.onerror = function(event) {
                console.log('onerror');
            }

        } catch (exception) {
            console.error(exception);
        }
    }

    getStatus() {
        return this.webSocket.readyState;
    }
    send(message) {

        if (this.webSocket.readyState == WebSocket.OPEN) {
            this.webSocket.send(message);

        } else {
            console.error('webSocket is not open. readyState=' + this.webSocket.readyState);
        }
    }
    disconnect() {
        if (this.webSocket.readyState == WebSocket.OPEN) {
            this.webSocket.close();

        } else {
            console.error('webSocket is not open. readyState=' + this.webSocket.readyState);
        }
    }
}

function showTime() {
    var date = new Date();
    var h = date.getHours(); // 0 - 23
    var m = date.getMinutes(); // 0 - 59
    var s = date.getSeconds(); // 0 - 59
    var session = "AM";
    var d = date.getDate();
    var mon = date.getMonth();
    var year = date.getFullYear();
    if (h == 0) {
        h = 12;
    }

    if (h > 12) {
        h = h - 12;
        session = "PM";
    }

    h = (h < 10) ? "0" + h : h;
    m = (m < 10) ? "0" + m : m;
    s = (s < 10) ? "0" + s : s;

    var time = d + "-" + mon + "-" + year + "  " + h + ":" + m + ":" + s + " " + session;
    document.getElementById("clock").innerText = time;
    document.getElementById("clock").textContent = time;

    setTimeout(showTime, 1000);

}

$(document).ready(function() {
    showTime();
    var input = document.getElementById('file-input');
    var infoArea = document.getElementById('file-name');
    console.log(input)
    if (input !== null) {
        input.addEventListener('change', showFileName);
    }

    function showFileName(event) {

        // the change event gives us the input it occurred in 
        var input = event.srcElement;

        // the input has an array of files in the `files` property, each one has a name that you can use. We're just using the name here.
        var fileName = input.files[0].name;

        // use fileName however fits your app best, i.e. add it into a div
        infoArea.textContent = 'File name: ' + fileName;
    }

    var calendars = bulmaCalendar.attach('[type="datetime-local"]', {
        displayMode: "default",
        type: "datetime",
        validateLabel: "Select",
        showClearButton: false
    });

    // To access to bulmaCalendar instance of an element
    var element = document.querySelector('#date');
    if (element) {
        // bulmaCalendar instance is available as element.bulmaCalendar
        element.bulmaCalendar.on('select', function(datepicker) {
            console.log(datepicker.data.value());
        });
    }


});