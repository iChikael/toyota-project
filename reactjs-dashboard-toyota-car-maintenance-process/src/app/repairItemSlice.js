import { HTTP_STATUS } from 'constants/global'
import repairItemApi from 'api/repairItemApi'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

const namespace = 'repairItems'

const initialState = {
  data: {
    repairItems: [{}],
    repairItem: {
      title: null,
      serviceArea: null,
    },
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllRepairItem = createAsyncThunk(
  `${namespace}/getAllRepairItem`,
  async (obj, { rejectWithValue }) => {
    return await repairItemApi
      .getAllRepairItem()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getRepairItemById = createAsyncThunk(
  `${namespace}/getRepairItemById`,
  async (obj, { rejectWithValue }) => {
    return await repairItemApi
      .getRepairItemById(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateRepairItemById = createAsyncThunk(
  `${namespace}/updateRepairItemById`,
  async (obj, { rejectWithValue }) => {
    return await repairItemApi
      .postUpdateRepairItemById(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const deleteRepairItemById = createAsyncThunk(
  `${namespace}/deleteRepairItemById`,
  async (id, { rejectWithValue }) => {
    return repairItemApi
      .deleteRepairItemById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const createRepairItem = createAsyncThunk(
  `${namespace}/createRepairItem`,
  async (obj, { rejectWithValue }) => {
    try {
      await repairItemApi.createRepairItem(obj).then((response) => {
        return response.data
      })
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

const repairItemSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getAllRepairItem.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllRepairItem.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.repairItems = payload
      })
      .addCase(createRepairItem.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createRepairItem.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.repairItem = payload
      })
      .addCase(createRepairItem.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getRepairItemById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.repairItem = payload
      })
      .addCase(updateRepairItemById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(updateRepairItemById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.repairItem = payload
      })
      .addCase(updateRepairItemById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = repairItemSlice

export default reducer
