import axiosClient from './axiosClient'
import { DASHBOARD_URL } from 'constants/global'

const dashboardApi = {
  getInfoDashboard: async () => {
    return await axiosClient.get(DASHBOARD_URL)
  },
}

export default dashboardApi
