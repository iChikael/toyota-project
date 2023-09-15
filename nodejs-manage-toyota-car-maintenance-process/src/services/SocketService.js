const axios = require('axios')

const RoomService = require('./RoomService')
const CashierService = require('./CashierService')
const TechnicalService = require('./TechnicalService')

var listRooms = []

class SocketService {
  connection(socket) {
    socket.use((packet, next) => {
      global.instanceAxios = axios.create({
        timeout: 10000,
        withCredentials: true,
      })
      next()
    })

    // console.log('Connected: ' + socket.id)
    // console.log("rooms ========");
    // console.log(socket.adapter.rooms);

    socket.emit('server-connection', socket.id)

    socket.on('create-room', (roomName) => {
      socket.join(roomName)
      socket.roomByRooms = roomName
      RoomService.createRoom(socket, roomName, listRooms)
    })

    socket.on('get-all-order-service', (accessToken) => {
      console.log('get-all-order-service')
      TechnicalService.getAllOrderService(socket, accessToken)
    })

    socket.on('get-all-order-service-done', (accessToken) => {
      console.log('get-all-order-service-done')
      CashierService.getAllOrderServiceDone(socket, accessToken)
    })
  }
}
module.exports = new SocketService()
