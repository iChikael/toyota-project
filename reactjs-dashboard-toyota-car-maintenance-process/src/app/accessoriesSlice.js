import { HTTP_STATUS } from 'constants/global'
import accessoryService from 'api/accessoryApi'
import { accessoryRoleService } from 'api/accessoryApi'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

const namespace = 'accessories'

const initialState = {
  data: {
    accessories: [{}],
    accessory: {
      name: null,
      code: null,
      price: null,
      quantity: null,
      unit: null,
      accessoryRole: null,
    },
    accessoryRole: [{}],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllAccessories = createAsyncThunk(
  `${namespace}/getAllAccessories`,
  async (obj, { rejectWithValue }) => {
    return await accessoryService
      .getAllAccessories()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAccessoryById = createAsyncThunk(
  `${namespace}/getAccessoryById`,
  async (obj, { rejectWithValue }) => {
    return await accessoryService
      .getAccessoryById(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateAccessoryById = createAsyncThunk(
  `${namespace}/updateAccessoryById`,
  async (obj, { rejectWithValue }) => {
    return await accessoryService
      .updateAccessoryById(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const createAccessory = createAsyncThunk(
  `${namespace}/createAccessory`,
  async (obj, { rejectWithValue }) => {
    return accessoryService
      .createAccessory(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const deleteAccessoryById = createAsyncThunk(
  `${namespace}/deleteAccessoryById`,
  async (id, { rejectWithValue }) => {
    return accessoryService
      .deleteById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const getAllAccessoryRole = createAsyncThunk(
  `${namespace}/getAllAccessoryRole`,
  async (obj, { rejectWithValue }) => {
    return await accessoryRoleService
      .getAllAccessoryRole()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const accessorySlice = createSlice({
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
      .addCase(getAllAccessories.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllAccessories.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.accessories = payload
      })
      .addCase(getAccessoryById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.accessory = payload
      })
      .addCase(createAccessory.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createAccessory.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.accessory = payload
      })
      .addCase(createAccessory.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(updateAccessoryById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(updateAccessoryById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.accessory = payload
      })
      .addCase(updateAccessoryById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllAccessoryRole.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.accessoryRole = payload
      })
      .addCase(getAllAccessoryRole.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = accessorySlice

export default reducer
