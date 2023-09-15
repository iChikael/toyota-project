import axiosClient from 'api/axiosClient'
import { SERVICE_AREA_URL } from 'constants/global'

const serviceAreaApi = {
  getAllServiceArea: async () => {
    return await axiosClient.get(SERVICE_AREA_URL)
  },
  createServiceArea: async (obj) => {
    return await axiosClient.post(SERVICE_AREA_URL + '/create', obj)
  },
  getServiceAreaById: async (id) => {
    return await axiosClient.get(`${SERVICE_AREA_URL}/${id}`)
  },
  postUpdateServiceAreaById: async (obj) => {
    return await axiosClient.patch(`${SERVICE_AREA_URL}/${obj.id}`, obj)
  },
  deleteServiceAreaById: async (id) => {
    return await axiosClient.delete(`${SERVICE_AREA_URL}/${id}`)
  },
  updateCurrentCapacityServiceArea: async (obj) => {
    return await axiosClient.patch(
      SERVICE_AREA_URL + '/update-current-capacity',
      obj
    )
  },
}

export default serviceAreaApi
