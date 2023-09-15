import axiosClient from 'api/axiosClient'
import axios from 'axios'
import { AUTH_STAFF_REGISTER_URL, STAFF } from 'constants/global'

const staffApi = {
  register: async (obj) => {
    return await axios({
      method: 'post',
      url: AUTH_STAFF_REGISTER_URL,
      data: obj,
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },
  getAllStaffs: async () => {
    return await axiosClient.get(STAFF.GET_ALL_STAFF)
  },
}

export default staffApi
