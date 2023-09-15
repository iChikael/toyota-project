import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { HTTP_STATUS } from 'constants/global'

import locationApi from '../api/locationApi'

const namespace = 'locationRegion'

const initialState = {
  data: {
    provinces: [],
    districts: [],
    wards: [],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
  formData: null,
}

export const getAllProivces = createAsyncThunk(
  `${namespace}/getAllProivces`,
  async ({ obj, rejectWithValue }) => {
    return await locationApi
      .getAllProivces()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllDistricts = createAsyncThunk(
  `${namespace}/getAllDistricts`,
  async (obj, { rejectWithValue }) => {
    return await locationApi
      .getAllDistricts(obj.id)
      .then((response) => {
        return response.data.results
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllWards = createAsyncThunk(
  `${namespace}/getAllWards`,
  async (obj, { rejectWithValue }) => {
    return await locationApi
      .getAllWards(obj.id)
      .then((response) => {
        return response.data.results
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const locationRegionSlice = createSlice({
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
      .addCase(getAllProivces.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllProivces.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.provinces = payload.results
      })
      .addCase(getAllProivces.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllDistricts.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllDistricts.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.districts = payload
      })
      .addCase(getAllDistricts.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllWards.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllWards.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.wards = payload
      })
      .addCase(getAllWards.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = locationRegionSlice

export default reducer
