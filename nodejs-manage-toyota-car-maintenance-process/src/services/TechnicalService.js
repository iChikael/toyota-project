const axios = require('axios')

const App = require('../models/App.model')

const getAllOrderService = async (socket, accessToken) => {
  try {
    axios.defaults.headers.common.Authorization = accessToken

    const getTechnicalData = await axios.get(App.GET_ALL_ORDER_SERVICE)

    let technicalTs = new Date().getTime()

    let obj = {
      clientId: socket.id,
      data: getTechnicalData.data,
      ts: technicalTs,
    }

    _io.sockets
      .in(socket.roomByRooms)
      .emit('get-all-order-service-success', obj)
  } catch (error) {
    socket.emit(
      'get-all-order-service-error',
      'Lỗi hệ thống, không tải được dữ liệu!'
    )
  }
}

let TechnicalService = {
  getAllOrderService,
}

module.exports = TechnicalService
