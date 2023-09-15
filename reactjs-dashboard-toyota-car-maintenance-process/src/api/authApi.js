import axiosClient from './axiosClient'
import { AUTH_STAFF_LOGIN_URL } from 'constants/global'

const authApi = {
  login: async (acount) => {
    return await axiosClient.post(AUTH_STAFF_LOGIN_URL, acount)
  },
}

export default authApi
