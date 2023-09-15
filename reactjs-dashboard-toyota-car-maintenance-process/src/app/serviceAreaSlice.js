import { HTTP_STATUS } from 'constants/global'
import serviceAreaApi from 'api/serviceAreaApi'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

const namespace = 'serviceArea'

const initialState = {
  data: {
    serviceAreas: [],
    serviceArea: {},
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllServiceArea = createAsyncThunk(
  `${namespace}/getAllServiceArea`,
  async (obj, { rejectWithValue }) => {
    return await serviceAreaApi
      .getAllServiceArea()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getServiceAreaById = createAsyncThunk(
  `${namespace}/getServiceAreaById`,
  async ({ id }, { rejectWithValue }) => {
    return await serviceAreaApi
      .getServiceAreaById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateServiceAreaById = createAsyncThunk(
  `${namespace}/updateServiceAreaById`,
  async (obj, { rejectWithValue }) => {
    return await serviceAreaApi
      .postUpdateServiceAreaById(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const deleteServiceAreaById = createAsyncThunk(
  `${namespace}/deleteServiceAreaById`,
  async ({ id }, { rejectWithValue }) => {
    try {
      await serviceAreaApi.deleteById(id)
      return null
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

export const createServiceArea = createAsyncThunk(
  `${namespace}/createServiceArea`,
  async (obj, { rejectWithValue }) => {
    try {
      await serviceAreaApi.createServiceArea(obj).then((response) => {
        return response.data
      })
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

export const updateCurrentCapacityServiceArea = createAsyncThunk(
  `${namespace}/updateCurrentCapacityServiceArea`,
  async (obj, { rejectWithValue }) => {
    try {
      await serviceAreaApi
        .updateCurrentCapacityServiceArea(obj)
        .then((response) => {
          return response.data
        })
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

const serviceAreaSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getAllServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllServiceArea.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.serviceAreas = payload
      })

      .addCase(getAllServiceArea.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getServiceAreaById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getServiceAreaById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.serviceArea = payload
      })

      .addCase(getServiceAreaById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(createServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createServiceArea.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.serviceArea = payload
      })
      .addCase(createServiceArea.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(updateCurrentCapacityServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateCurrentCapacityServiceArea.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.serviceArea = payload
        }
      )
      .addCase(
        updateCurrentCapacityServiceArea.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(updateServiceAreaById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(updateServiceAreaById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.serviceArea = payload
      })
      .addCase(updateServiceAreaById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = serviceAreaSlice

export default reducer
