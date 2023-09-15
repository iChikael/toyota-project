import axiosClient from 'api/axiosClient'
import { LOCATION_URL } from 'constants/global'

const locationApi = {
  getAllProivces: async () => {
    return await axiosClient.get(LOCATION_URL)
  },
  getAllDistricts: async (provinceId) => {
    return await axiosClient.get(LOCATION_URL + 'district/' + provinceId)
  },
  getAllWards: async (districtId) => {
    return await axiosClient.get(LOCATION_URL + 'ward/' + districtId)
  },
}

export default locationApi
