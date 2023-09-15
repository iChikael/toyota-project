import axiosClient from 'api/axiosClient'
import { ACCESSORY_URL, ACCESSORY_ROLE_URL } from 'constants/global'

const accessoryApi = {
  getAllAccessories: async () => {
    return await axiosClient.get(ACCESSORY_URL)
  },
  createAccessory: async (obj) => {
    return await axiosClient.post(ACCESSORY_URL + '/create', obj)
  },
  getAccessoryById: async (id) => {
    return await axiosClient.get(`${ACCESSORY_URL}/${id}`)
  },
  updateAccessoryById: async (obj) => {
    return await axiosClient.patch(`${ACCESSORY_URL}/${obj.id}`, obj)
  },
  deleteById: async (accessoryId) => {
    return await axiosClient.delete(`${ACCESSORY_URL}/${accessoryId}`)
  },
}

export const accessoryRoleService = {
  getAllAccessoryRole: async () => {
    return await axiosClient.get(`${ACCESSORY_ROLE_URL}`)
  },
}

export default accessoryApi
