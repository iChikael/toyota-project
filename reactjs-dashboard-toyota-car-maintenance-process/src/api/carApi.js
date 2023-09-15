import axiosClient from 'api/axiosClient'
import { CAR_URL } from 'constants/global'
import axios from 'axios'

const carApi = {
  getAllCars: async () => {
    return await axiosClient.get(CAR_URL)
  },
  createCar: async (obj) => {
    return await axios({
      method: 'post',
      url: CAR_URL + '/create',
      data: obj,
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },
  getCarByid: async (id) => {
    return await axiosClient.get(`${CAR_URL}/${id}`)
  },
  postUpdateCarById: async (id) => {
    return await axiosClient.patch(`${CAR_URL}/${id}`)
  },
  deleteCarById: async (id) => {
    return await axiosClient.delete(`${CAR_URL}/${id}`)
  },
}

export default carApi
