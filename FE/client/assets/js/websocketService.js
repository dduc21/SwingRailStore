// websocketService.js
let stompClient = null;

function connect() {
    const socket = new SockJS('/ws'); // Địa chỉ endpoint WebSocket
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        // Subscribe để nhận dữ liệu cập nhật từ server
        stompClient.subscribe('/topic/dataUpdates', function (dataUpdate) {
            const data = dataUpdate.body;
            console.log("Received data: ", data);
            updateData(data);  // Gọi hàm updateData để cập nhật dữ liệu
        });
    });
}

function updateData(data) {
    console.log("Dữ liệu đã nhận: ", data);
}

// Đăng ký các hàm vào đối tượng window để có thể truy cập từ bất kỳ đâu
window.websocketService = {
    connect,
    updateData
};
