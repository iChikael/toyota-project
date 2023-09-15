import axiosClient from 'api/axiosClient'
import { CASHIER } from 'constants/global'

const cashierAPI = {
  getAllOrderServicesDone: async () => {
    return await axiosClient.get(CASHIER.GET_ALL_ORDER_SERVICE_DONE)
  },
  getOrderServiceDoneById: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_ORDER_SERVICE_DONE_BY_ID + '/' + id
    )
  },
  getAllOrderMaintenancesDoneByOrderServiceId: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_ALL_ORDER_MAINTENANCE_DONE_BY_ORDER_SERVICE_ID + '/' + id
    )
  },
  getAllOrderMaintenanceItemsDoneByOrderServiceId: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_ALL_ORDER_MAINTENANCE_ITEM_DONE_BY_ORDER_SERVICE_ID + '/' + id
    )
  },

  getAllOrderRepairItemsDoneByOrderServiceId: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_ALL_ORDER_REPAIR_ITEM_DONE_BY_ORDER_SERVICE_ID + '/' + id
    )
  },

  paymentOrderService: async (obj) => {
    return await axiosClient.post(CASHIER.PAYMENT_ORRDER_SERVICE, obj)
  },
  getAllBillServices: async () => {
    return await axiosClient.get(CASHIER.GET_ALL_BILL_SERVICE)
  },
  getBillServiceById: async (id) => {
    return await axiosClient.get(CASHIER.GET_BILL_SERVICE_BY_ID + '/' + id)
  },
  getManagementCarPlateByCarPlate: async (carPlate) => {
    return await axiosClient.get(
      CASHIER.GET_MANAGEMENT_CAR_PLATE_BY_CAR_PLATE + '/' + carPlate
    )
  },
  getTimeCreateOrderService: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_TIME_CREATE_ORDER_SERVICE + '/' + id
    )
  },
  getTimeDoneOrderService: async (id) => {
    return await axiosClient.get(CASHIER.GET_TIME_DONE_ORDER_SERVICE + '/' + id)
  },
  getAllBillServiceDetailByBillServiceId: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_ALL_BILL_SERVICE_DETAIL_BY_BILL_SERVICE_ID + '/' + id
    )
  },
  getAllBillServiceDetailAccessoryByBillServiceDetailId: async (id) => {
    return await axiosClient.get(
      CASHIER.GET_ALL_BILL_SERVICE_DETAIL_ACCESSORY_BY_BILL_SERVICE_DETAIL_ID +
        '/' +
        id
    )
  },
  getTopFiveBillServicesRecent: async () => {
    return await axiosClient.get(CASHIER.GET_TOP_5_BILL_SERVICE_RECENT)
  },
  getRevenueByDay: async (date) => {
    return await axiosClient.post(
      CASHIER.GET_BILL_SERVICE_BY_DAY + '?date=' + date
    )
  },
  getRevenueByMonth: async (month) => {
    return await axiosClient.post(
      CASHIER.GET_BILL_SERVICE_BY_MONTH + '?month=' + month
    )
  },
  getRevenueByYear: async (year) => {
    return await axiosClient.post(
      CASHIER.GET_BILL_SERVICE_BY_YEAR + '?year=' + year
    )
  },
  getRevenueByPeriod: async (dateStart, dateEnd) => {
    return await axiosClient.post(
      CASHIER.GET_BILL_SERVICE_BY_PERIOD +
        '?dateStart=' +
        dateStart +
        '&dateEnd=' +
        dateEnd
    )
  },
}

export default cashierAPI
