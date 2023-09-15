import axiosClient from 'api/axiosClient'
import { VEHICLE_URL } from 'constants/global'

const vehicleApi = {
  getAllVehicles: async () => {
    return await axiosClient.get(VEHICLE_URL)
  },
  createVehicle: async (obj) => {
    return await axiosClient.post(VEHICLE_URL + '/create', obj)
  },
  getVehicleByid: async (id) => {
    return await axiosClient.post(`${VEHICLE_URL}/${id}`)
  },
  postUpdateVehicleById: async (id) => {
    return await axiosClient.patch(`${VEHICLE_URL}/${id}`)
  },
  deleteVehicleById: async (id) => {
    return await axiosClient.delete(`${VEHICLE_URL}/${id}`)
  },
}

export default vehicleApi
