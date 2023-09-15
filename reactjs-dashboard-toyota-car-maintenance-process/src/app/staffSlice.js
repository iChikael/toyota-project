import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { HTTP_STATUS } from 'constants/global'

import staffApi from '../api/staffApi'
import Helper from 'utils/Helper'

const namespace = 'staff'

const initialState = {
  data: {
    register: {
      username: null,
      password: null,
      fullName: null,
      email: null,
      dob: null,
      provinceId: null,
      provinceName: null,
      districtId: null,
      districtName: null,
      wardId: null,
      wardName: null,
      address: null,
      identificationNumber: null,
      avatar: null,
      identificationImageBefore: null,
      identificationImageAfter: null,
      idUserRole: null,
    },
    staffs: [],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
  formData: null,
}

export const getAllStaffs = createAsyncThunk(
  `${namespace}/getAllStaffs`,
  async (obj, { rejectWithValue }) => {
    return await staffApi
      .getAllStaffs()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const registerStaff = createAsyncThunk(
  `${namespace}/registerStaff`,
  async (obj, { rejectWithValue }) => {
    return await staffApi
      .register(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const staffSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {
    setFormData: (state, { payload }) => {
      const formData = payload.formData
      state.formData = formData
    },
  },
  extraReducers(builder) {
    builder
      .addCase(getAllStaffs.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllStaffs.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.staffs = payload
        state.data.staffs.forEach((item) => {
          item.phone = Helper.formatPhoneNumber(item.phone)
        })
      })
      .addCase(getAllStaffs.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(registerStaff.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(registerStaff.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.formData = payload.formData
      })
      .addCase(registerStaff.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = staffSlice

export default reducer
