import axiosClient from 'api/axiosClient'
import { CAR_QUEUE_URL } from 'constants/global'

const carQueueApi = {
  getAllCarQueue: async () => {
    return await axiosClient.get(CAR_QUEUE_URL)
  },
  createCarQueue: async (obj) => {
    return await axiosClient.post(CAR_QUEUE_URL + '/create', obj)
  },
  getCarQueueByid: async (id) => {
    return await axiosClient.get(`${CAR_QUEUE_URL}/${id}`)
  },
  postUpdateCarQueueById: async (id) => {
    return await axiosClient.patch(`${CAR_QUEUE_URL}/${id}`)
  },
  deleteCarQueueById: async (id) => {
    return await axiosClient.delete(`${CAR_QUEUE_URL}/${id}`)
  },
  updateCarQueueById: async (obj) => {
    return await axiosClient.patch(`${CAR_QUEUE_URL}/${obj.id}`, obj)
  },
}

export default carQueueApi
