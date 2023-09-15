import axiosClient from 'api/axiosClient'
import axios from 'axios'
import { AUTH_CUSTOMER_REGISTER_URL, CUSTOMER } from 'constants/global'

const customerApi = {
  register: async (obj) => {
    return await axios({
      method: 'post',
      url: AUTH_CUSTOMER_REGISTER_URL,
      data: obj,
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },
  getAllCustomers: async () => {
    return await axiosClient.get(CUSTOMER.GET_ALL_CUSTOMER)
  },
}

export default customerApi
