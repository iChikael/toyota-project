import axiosClient from 'api/axiosClient'
import { REPAIR_ITEM_URL } from 'constants/global'

const repairItemApi = {
  getAllRepairItem: async () => {
    return await axiosClient.get(REPAIR_ITEM_URL)
  },
  createRepairItem: async (obj) => {
    return await axiosClient.post(REPAIR_ITEM_URL + '/create', obj)
  },
  getRepairItemById: async (id) => {
    return await axiosClient.get(`${REPAIR_ITEM_URL}/${id}`)
  },
  postUpdateRepairItemById: async (obj) => {
    return await axiosClient.patch(`${REPAIR_ITEM_URL}/${obj.id}`, obj)
  },
  deleteRepairItemById: async (id) => {
    return await axiosClient.delete(`${REPAIR_ITEM_URL}/${id}`)
  },
}

export default repairItemApi
