import axiosClient from 'api/axiosClient'
import axios from 'axios'
import { TECHNICAL } from 'constants/global'

const technicalApi = {
  getAllOrderServices: async () => {
    return await axiosClient.get(TECHNICAL.GET_ALL_ORDER_SERVICE)
  },
  getOrderServiceById: async (id) => {
    return await axiosClient.get(TECHNICAL.GET_ORDER_SERVICE_BY_ID + '/' + id)
  },
  getAllOrderServiceByCurrentServiceArea: async (serviceAreaId) => {
    return await axiosClient.get(
      TECHNICAL.GET_ALL_ORDER_SERVICE_BY_CURRENT_SERVICE_AREA +
        '/' +
        serviceAreaId
    )
  },
  getAllOrderMaintenancesByOrderServiceId: async (id) => {
    return await axiosClient.get(
      TECHNICAL.GET_ALL_ORDER_MAINTENANCE_BY_ORDER_SERVICE_ID + '/' + id
    )
  },
  getAllOrderMaintenanceItemsByOrderServiceId: async (id) => {
    return await axiosClient.get(
      TECHNICAL.GET_ALL_ORDER_MAINTENANCE_ITEM_BY_ORDER_SERVICE_ID + '/' + id
    )
  },
  getAllOrderMaintenanceItemsByCurrentServiceArea: async (obj) => {
    return await axiosClient.post(
      TECHNICAL.GET_ALL_ORDER_MAINTENANCE_ITEM_BY_CURRENT_SERVICE_AREA,
      obj
    )
  },
  getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId: async (id) => {
    return await axiosClient.get(
      TECHNICAL.GET_ALL_ORDER_MAINTENANCE_ITEM_ACCESSORY_BY_ORDER_MAINTENANCE_ITEM_ID +
        '/' +
        id
    )
  },
  getAllOrderRepairItemsByOrderServiceId: async (id) => {
    return await axiosClient.get(
      TECHNICAL.GET_ALL_ORDER_REPAIR_ITEM_BY_ORDER_SERVICE_ID + '/' + id
    )
  },
  getAllOrderRepairItemsByCurrentServiceArea: async (obj) => {
    return await axiosClient.post(
      TECHNICAL.GET_ALL_ORDER_REPAIR_ITEM_BY_CURRENT_SERVICE_AREA,
      obj
    )
  },
  getAllOrderRepairItemAccessoriesByOrderRepairItemId: async (id) => {
    return await axiosClient.get(
      TECHNICAL.GET_ALL_ORDER_REPAIR_ITEM_ACCESSORY_BY_ORDER_REPAIR_ITEM_ID +
        '/' +
        id
    )
  },
  updateStatusMaintenanceItemByServiceArea: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_MAINTENANCE_ITEM_BY_SERVICE_AREA,
      obj
    )
  },
  updateStatusRepairItemByServiceArea: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_REPAIR_ITEM_BY_SERVICE_AREA,
      obj
    )
  },
  updateStatusMaintenanceItemByServiceAreaDone: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_MAINTENANCE_ITEM_BY_SERVICE_AREA_DONE,
      obj
    )
  },
  updateStatusRepairItemByServiceAreaDone: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_REPAIR_ITEM_BY_SERVICE_AREA_DONE,
      obj
    )
  },
  updateCurrentServiceAreaOrderService: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_CURRENT_SERVICE_AREA_ORDER_SERVICE,
      obj
    )
  },

  updateStatusCarQueueByOrderService: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_CAR_QUEUE_BY_ORDER_SERVICE,
      obj
    )
  },
  updateStatusOrderMaintenanceByOrderServiceId: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_ORDER_MAINTENANCE_BY_ORDER_SERVICE,
      obj
    )
  },
  updateStatusMaintenanceItemByServiceAreaDoneAll: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_MAINTENANCE_ITEM_BY_SERVICE_AREA_DONE_ALL,
      obj
    )
  },
  updateStatusRepairItemByServiceAreaDoneAll: async (obj) => {
    return await axiosClient.patch(
      TECHNICAL.UPDATE_STATUS_REPAIR_ITEM_BY_SERVICE_AREA_DONE_ALL,
      obj
    )
  },
}

export default technicalApi
