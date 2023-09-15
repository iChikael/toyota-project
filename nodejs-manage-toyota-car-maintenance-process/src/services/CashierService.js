const axios = require('axios')

const App = require('../models/App.model')

const getAllOrderServiceDone = async (socket, accessToken) => {
  try {
    axios.defaults.headers.common.Authorization = accessToken

    const getCashierData = await axios.get(App.GET_ALL_ORDER_SERVICE_DONE)

    let cashierTs = new Date().getTime()

    let obj = {
      clientId: socket.id,
      data: getCashierData.data,
      ts: cashierTs,
    }

    _io.sockets
      .in(socket.roomByRooms)
      .emit('get-all-order-service-done-success', obj)
  } catch (error) {
    socket.emit(
      'get-all-order-service-done-error',
      'Lỗi hệ thống, không tải được dữ liệu!'
    )
  }
}

let CashierService = {
  getAllOrderServiceDone,
}

module.exports = CashierService
