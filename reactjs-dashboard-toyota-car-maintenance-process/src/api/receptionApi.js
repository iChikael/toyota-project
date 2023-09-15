import axiosClient from 'api/axiosClient'
import { RECEPTION } from 'constants/global'

const receptionApi = {
  searchCarPlate: async (key) => {
    return await axiosClient.get(RECEPTION.SEARCH_CAR_PLATE + key)
  },
  getAllCarPlates: async () => {
    return await axiosClient.get(RECEPTION.GET_ALL_CAR_PLATE)
  },
  getAllMaintenanceItem: async () => {
    return await axiosClient.get(RECEPTION.GET_ALL_MAINTENANCE_ITEM)
  },
  getAllMaintenances: async () => {
    return await axiosClient.get(RECEPTION.GET_ALL_MAINTENANCE)
  },
  getAllMaintenanceMaintenanceItemsByMaintenanceId: async (maintenanceId) => {
    return await axiosClient.get(
      RECEPTION.GET_ALL_MAINTENANCE_MAINTENANCE_ITEM_BY_MAINTENANCE +
        '/' +
        maintenanceId
    )
  },

  getAllRepairItems: async () => {
    return await axiosClient.get(RECEPTION.GET_ALL_REPAIR_ITEM)
  },
  searchCustomerByPhone: async (phone) => {
    return await axiosClient.get(RECEPTION.SEARCH_CUSTOMER_BY_PHONE + phone)
  },
  searchCarByName: async (name) => {
    return await axiosClient.get(RECEPTION.SEARCH_CAR_BY_NAME + name)
  },
  createCarPlate: async (obj) => {
    return await axiosClient.post(RECEPTION.CREATE_CAR_PLATE + '/create', obj)
  },
  getManagementCarPlateByCarPlate: async (carPlate) => {
    return await axiosClient.get(RECEPTION.GET_BY_CAR_PLATE + '/' + carPlate)
  },
  createOrderService: async (obj) => {
    return await axiosClient.post(RECEPTION.CREATE_ORDER_SERVICE, obj)
  },
  getAllCarQueues: async () => {
    return await axiosClient.get(RECEPTION.GET_ALL_CAR_QUEUE)
  },
  getAllMaintenanceItemAccessoryByMaintenanceItemId: async (
    maintenanceItemId
  ) => {
    return await axiosClient.get(
      RECEPTION.GET_ALL_MAINTENANCE_ITEM_ACCESSORY_BY_MAINTENANCE_ITEM_ID +
        '/' +
        maintenanceItemId
    )
  },
  getAllRepairItemAccessoryByRepairItemId: async (repairItemId) => {
    return await axiosClient.get(
      RECEPTION.GET_ALL_REPAIR_ITEM_ACCESSORY_BY_REPAIR_ITEM_ID +
        '/' +
        repairItemId
    )
  },
  getCarPlateById: async (carPlateId) => {
    return await axiosClient.get(
      RECEPTION.GET_CAR_PLATE_BY_ID + '/' + carPlateId
    )
  },
}

export default receptionApi
