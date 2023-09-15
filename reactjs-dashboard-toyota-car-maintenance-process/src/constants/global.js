const SERVER_API = process.env.REACT_APP_API_URL
export const SOCKET_SERVER = process.env.REACT_APP_SOCKET_SERVER
export const COUDINARY_URL = process.env.REACT_APP_COUDINARY_URL

export const LOCATION_URL = 'https://vapi.vnappmob.com/api/province/'
export const AUTH_STAFF_LOGIN_URL = SERVER_API + '/auth/staff/login'
export const AUTH_STAFF_REGISTER_URL = SERVER_API + '/auth/staff/register'

export const AUTH_CUSTOMER_REGISTER_URL = SERVER_API + '/auth/customer/register'
export const ACCESSORY_URL = SERVER_API + '/storage'
export const ACCESSORY_ROLE_URL = SERVER_API + '/storage/accessory-role'
export const CAR_QUEUE_URL = SERVER_API + '/car-queues'
export const REPAIR_ITEM_URL = SERVER_API + '/repair-items'
export const SERVICE_AREA_URL = SERVER_API + '/service-areas'
export const CAR_URL = SERVER_API + '/cars'
export const VEHICLE_URL = SERVER_API + '/vehicle'

export const DASHBOARD_URL = SERVER_API + '/dashboards'

export const HTTP_STATUS = Object.freeze({
  IDLE: 'IDLE',
  PENDING: 'PENDING',
  FULFILLED: 'FULFILLED',
  REJECTED: 'REJECTED',
})

export const ROLES = Object.freeze({
  ADMIN: 'ADMIN',
  RECEPTION: 'RECEPTION',
  CASHIER: 'CASHIER',
  TECHNICAL: 'TECHNICAL',
  CUSTOMER: 'CUSTOMER',
})

export const AUTH = Object.freeze({
  ACCESS_TOKEN: 'toyota_accessToken',
})

export const COUDINARY = {
  url: COUDINARY_URL,
  SCALE_IMAGE_300_300: 'c_limit,w_300,h_300,q_100',
  SCALE_IMAGE_180_180: 'c_limit,w_180,h_180,q_100',
  SCALE_IMAGE_120_120: 'c_limit,w_120,h_120,q_100',
}

export const STAFF = {
  GET_ALL_STAFF: `${SERVER_API}/staffs`,
}

export const CUSTOMER = {
  GET_ALL_CUSTOMER: `${SERVER_API}/customers`,
}

export const RECEPTION = {
  SEARCH_CAR_PLATE: `${SERVER_API}/car-plates/search?key=`,
  SEARCH_CUSTOMER_BY_PHONE: `${SERVER_API}/customers/search?phone=`,
  SEARCH_CAR_BY_NAME: `${SERVER_API}/cars/search?name=`,
  GET_ALL_CAR_PLATE: `${SERVER_API}/car-plates`,
  CREATE_CAR_PLATE: `${SERVER_API}/car-plates`,
  GET_BY_CAR_PLATE: `${SERVER_API}/car-plates`,
  GET_ALL_MAINTENANCE: `${SERVER_API}/maintenances`,
  GET_ALL_MAINTENANCE_ITEM: `${SERVER_API}/maintenance-items`,
  GET_ALL_MAINTENANCE_MAINTENANCE_ITEM_BY_MAINTENANCE: `${SERVER_API}/maintenance-maintenance-items`,
  GET_ALL_REPAIR_ITEM: `${SERVER_API}/repair-items`,
  CREATE_ORDER_SERVICE: `${SERVER_API}/order-services`,
  GET_ALL_CAR_QUEUE: `${SERVER_API}/car-queues`,
  GET_ALL_MAINTENANCE_ITEM_ACCESSORY_BY_MAINTENANCE_ITEM_ID: `${SERVER_API}/maintenance-item-accessories`,
  GET_ALL_REPAIR_ITEM_ACCESSORY_BY_REPAIR_ITEM_ID: `${SERVER_API}/repair-item-accessories`,
  GET_CAR_PLATE_BY_ID: `${SERVER_API}/car-plates/id`,
}

export const TECHNICAL = {
  GET_ALL_ORDER_SERVICE: `${SERVER_API}/order-services`,
  GET_ORDER_SERVICE_BY_ID: `${SERVER_API}/order-services`,
  GET_ALL_ORDER_SERVICE_BY_CURRENT_SERVICE_AREA: `${SERVER_API}/order-services/service-area`,
  GET_ALL_ORDER_MAINTENANCE_BY_ORDER_SERVICE_ID: `${SERVER_API}/order-maintenances`,
  GET_ALL_ORDER_MAINTENANCE_ITEM_BY_CURRENT_SERVICE_AREA: `${SERVER_API}/order-maintenance-items/service-area`,
  GET_ALL_ORDER_MAINTENANCE_ITEM_BY_ORDER_SERVICE_ID: `${SERVER_API}/order-maintenance-items`,
  GET_ALL_ORDER_MAINTENANCE_ITEM_ACCESSORY_BY_ORDER_MAINTENANCE_ITEM_ID: `${SERVER_API}/order-maintenance-item-accessories`,
  GET_ALL_ORDER_REPAIR_ITEM_BY_ORDER_SERVICE_ID: `${SERVER_API}/order-repair-items`,
  GET_ALL_ORDER_REPAIR_ITEM_BY_CURRENT_SERVICE_AREA: `${SERVER_API}/order-repair-items/service-area`,
  GET_ALL_ORDER_REPAIR_ITEM_ACCESSORY_BY_ORDER_REPAIR_ITEM_ID: `${SERVER_API}/order-repair-item-accessories`,

  UPDATE_STATUS_CAR_QUEUE_BY_ORDER_SERVICE: `${SERVER_API}/order-services/update-status`,
  UPDATE_STATUS_ORDER_MAINTENANCE_BY_ORDER_SERVICE: `${SERVER_API}/order-maintenances/update-status`,

  UPDATE_STATUS_MAINTENANCE_ITEM_BY_SERVICE_AREA: `${SERVER_API}/order-maintenance-items/update-status`,
  UPDATE_STATUS_REPAIR_ITEM_BY_SERVICE_AREA: `${SERVER_API}/order-repair-items/update-status`,
  UPDATE_STATUS_MAINTENANCE_ITEM_BY_SERVICE_AREA_DONE: `${SERVER_API}/order-maintenance-items/update-status-done`,
  UPDATE_STATUS_REPAIR_ITEM_BY_SERVICE_AREA_DONE: `${SERVER_API}/order-repair-items/update-status-done`,

  UPDATE_CURRENT_SERVICE_AREA_ORDER_SERVICE: `${SERVER_API}/order-service-current-service-areas/update-service-area`,
  UPDATE_STATUS_MAINTENANCE_ITEM_BY_SERVICE_AREA_DONE_ALL: `${SERVER_API}/order-maintenance-items/update-status-done-all`,
  UPDATE_STATUS_REPAIR_ITEM_BY_SERVICE_AREA_DONE_ALL: `${SERVER_API}/order-repair-items/update-status-done-all`,
}

export const CASHIER = {
  GET_ALL_ORDER_SERVICE_DONE: `${SERVER_API}/order-services/done`,
  GET_ORDER_SERVICE_DONE_BY_ID: `${SERVER_API}/order-services`,
  GET_ALL_ORDER_MAINTENANCE_DONE_BY_ORDER_SERVICE_ID: `${SERVER_API}/order-maintenances`,
  GET_ALL_ORDER_MAINTENANCE_ITEM_DONE_BY_ORDER_SERVICE_ID: `${SERVER_API}/order-maintenance-items`,
  GET_ALL_ORDER_REPAIR_ITEM_DONE_BY_ORDER_SERVICE_ID: `${SERVER_API}/order-repair-items`,
  PAYMENT_ORRDER_SERVICE: `${SERVER_API}/order-services/payment`,
  GET_ALL_BILL_SERVICE: `${SERVER_API}/bill-services`,
  GET_BILL_SERVICE_BY_ID: `${SERVER_API}/bill-services`,
  GET_ALL_BILL_SERVICE_DETAIL_BY_BILL_SERVICE_ID: `${SERVER_API}/bill-service-details`,
  GET_ALL_BILL_SERVICE_DETAIL_ACCESSORY_BY_BILL_SERVICE_DETAIL_ID: `${SERVER_API}/bill-service-detail-accessories`,
  GET_MANAGEMENT_CAR_PLATE_BY_CAR_PLATE: `${SERVER_API}/car-plates`,
  GET_TIME_CREATE_ORDER_SERVICE: `${SERVER_API}/order-service-current-service-areas/time-create`,
  GET_TIME_DONE_ORDER_SERVICE: `${SERVER_API}/order-service-current-service-areas/time-done`,
  GET_TOP_5_BILL_SERVICE_RECENT: `${SERVER_API}/bill-services/top-five`,
  GET_BILL_SERVICE_BY_DAY: `${SERVER_API}/bill-services/revenue-by-day`,
  GET_BILL_SERVICE_BY_MONTH: `${SERVER_API}/bill-services/revenue-by-month`,
  GET_BILL_SERVICE_BY_YEAR: `${SERVER_API}/bill-services/revenue-by-year`,
  GET_BILL_SERVICE_BY_PERIOD: `${SERVER_API}/bill-services/revenue-by-period`,
}
