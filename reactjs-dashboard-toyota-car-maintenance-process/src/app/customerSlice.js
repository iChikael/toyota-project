import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { HTTP_STATUS } from 'constants/global'

import customerApi from 'api/customerApi'

const namespace = 'customers'

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
    customers: [],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
  formData: null,
}

export const getAllCustomers = createAsyncThunk(
  `${namespace}/getAllCustomers`,
  async (obj, { rejectWithValue }) => {
    return await customerApi
      .getAllCustomers()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const registerCustomer = createAsyncThunk(
  `${namespace}/registerCustomer`,
  async (obj, { rejectWithValue }) => {
    return await customerApi
      .register(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const customerSlice = createSlice({
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
      .addCase(getAllCustomers.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllCustomers.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.customers = payload
      })
      .addCase(getAllCustomers.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(registerCustomer.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(registerCustomer.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.formData = payload.formData
      })
      .addCase(registerCustomer.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = customerSlice

export default reducer
