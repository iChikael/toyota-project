const SERVER_API = process.env.SERVER_API
const BASE_ORDER_SERVICE_API = SERVER_API + '/v1/order-services'

class App {
  static GET_ALL_ORDER_SERVICE_DONE = `${BASE_ORDER_SERVICE_API}/done`
}

module.exports = App
