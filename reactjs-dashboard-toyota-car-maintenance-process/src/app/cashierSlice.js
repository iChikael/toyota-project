import { HTTP_STATUS } from 'constants/global'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import cashierAPI from 'api/cashierAPI'
import Helper from 'utils/Helper'

const namespace = 'cashier'

const initialState = {
  data: {
    orderServicesDone: [],
    viewOrderServicesDone: [],
    orderServiceDone: {},
    orderMaintenanceDoneByOrderServiceId: {},
    orderMaintenanceItemsDoneByOrderServiceId: [],
    orderMaintenanceItemAccessoriesDoneByOrderMaintenanceItemId: [],
    orderRepairItemsDoneByOrderServiceId: [],
    orderRepairItemAccessoriesDoneByOrderRepairItemId: [],
    billServices: [],
    viewBillServices: [],
    billServiceDetails: [],
    billServiceDetailAccessories: [],
    billService: {},
    managementCarPlate: {},
    orderServiceTimeCreate: [],
    orderServiceTimeDone: {},
    billServiceTopFiveRecents: [],
    revenueByDay: {},
    viewBillServicesByDay: [],
    revenueByMonth: {},
    viewBillServicesByMonth: [],
    revenueByYear: {},
    viewBillServicesByYear: [],
    revenueByPeriod: {},
    viewBillServicesByPeriod: [],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllOrderServicesDone = createAsyncThunk(
  `${namespace}/getAllOrderServicesDone`,
  async (obj, { rejectWithValue }) => {
    return await cashierAPI
      .getAllOrderServicesDone()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getOrderServiceDoneById = createAsyncThunk(
  `${namespace}/getOrderServiceDoneById`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getOrderServiceDoneById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderMaintenancesDoneByOrderServiceId = createAsyncThunk(
  `${namespace}/getAllOrderMaintenancesDoneByOrderServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getAllOrderMaintenancesDoneByOrderServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderMaintenanceItemsDoneByOrderServiceId = createAsyncThunk(
  `${namespace}/getAllOrderMaintenanceItemsDoneByOrderServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getAllOrderMaintenanceItemsDoneByOrderServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderRepairItemsDoneByOrderServiceId = createAsyncThunk(
  `${namespace}/getAllOrderRepairItemsDoneByOrderServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getAllOrderRepairItemsDoneByOrderServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const paymentOrderService = createAsyncThunk(
  `${namespace}/paymentOrderService`,
  async (obj, { rejectWithValue }) => {
    return await cashierAPI
      .paymentOrderService(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllBillServices = createAsyncThunk(
  `${namespace}/getAllBillServices`,
  async (obj, { rejectWithValue }) => {
    return cashierAPI
      .getAllBillServices()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const getBillServiceById = createAsyncThunk(
  `${namespace}/getBillServiceById`,
  async ({ id }, { rejectWithValue }) => {
    return cashierAPI
      .getBillServiceById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const getManagementCarPlateByCarPlate = createAsyncThunk(
  `${namespace}/getManagementCarPlateByCarPlate`,
  async ({ carPlate }, { rejectWithValue }) => {
    return await cashierAPI
      .getManagementCarPlateByCarPlate(carPlate)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getTimeCreateOrderService = createAsyncThunk(
  `${namespace}/getTimeCreateOrderService`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getTimeCreateOrderService(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const getTimeDoneOrderService = createAsyncThunk(
  `${namespace}/getTimeDoneOrderService`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getTimeDoneOrderService(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllBillServiceDetailByBillServiceId = createAsyncThunk(
  `${namespace}/getAllBillServiceDetailByBillServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await cashierAPI
      .getAllBillServiceDetailByBillServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllBillServiceDetailAccessoryByBillServiceDetailId =
  createAsyncThunk(
    `${namespace}/getAllBillServiceDetailAccessoryByBillServiceDetailId`,
    async ({ id }, { rejectWithValue }) => {
      return await cashierAPI
        .getAllBillServiceDetailAccessoryByBillServiceDetailId(id)
        .then((response) => {
          return response.data
        })
        .catch((error) => {
          return rejectWithValue(error)
        })
    }
  )

export const getTopFiveBillServicesRecent = createAsyncThunk(
  `${namespace}/getTopFiveBillServicesRecent`,
  async (obj, { rejectWithValue }) => {
    return await cashierAPI
      .getTopFiveBillServicesRecent()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getRevenueByDay = createAsyncThunk(
  `${namespace}/getRevenueByDay`,
  async ({ date }, { rejectWithValue }) => {
    return await cashierAPI
      .getRevenueByDay(date)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getRevenueByMonth = createAsyncThunk(
  `${namespace}/getRevenueByMonth`,
  async ({ month }, { rejectWithValue }) => {
    return await cashierAPI
      .getRevenueByMonth(month)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const getRevenueByYear = createAsyncThunk(
  `${namespace}/getRevenueByYear`,
  async ({ year }, { rejectWithValue }) => {
    return await cashierAPI
      .getRevenueByYear(year)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const getRevenueByPeriod = createAsyncThunk(
  `${namespace}/getRevenueByPeriod`,
  async ({ dateStart, dateEnd }, { rejectWithValue }) => {
    return await cashierAPI
      .getRevenueByPeriod(dateStart, dateEnd)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const carSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {
    setAllOrderServicesDone: (state, { payload }) => {
      state.status = HTTP_STATUS.FULFILLED
      state.data.orderServicesDone = payload
      const viewOrderServicesDoneNew = []
      if (payload.length) {
        payload.map((item) => {
          viewOrderServicesDoneNew.push({
            id: item.id,
            customerName: item.carQueue.fullName,
            carPlate: Helper.formatCarNumberPlate(
              item.carQueue.carNumberPlates
            ),
            carName: item.car.title,
            carVehicle: item.car.vehicle.name,
            customerReq: item.customerReq,
            doEarly: item.doEarly,
            timeMinute: item.timeMinute,
            status: item.carQueue.status,
            currentServiceArea: item.currentServiceArea,
            statusPayment: item.statusPayment,
          })
        })
      }

      state.data.viewOrderServicesDone = viewOrderServicesDoneNew
    },
  },
  extraReducers(builder) {
    builder
      .addCase(getAllOrderServicesDone.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllOrderServicesDone.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderServicesDone = payload
        const viewOrderServicesDoneNew = []
        if (payload.length) {
          payload.map((item) => {
            viewOrderServicesDoneNew.push({
              id: item.id,
              customerName: item.carQueue.fullName,
              carPlate: Helper.formatCarNumberPlate(
                item.carQueue.carNumberPlates
              ),
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              customerReq: item.customerReq,
              doEarly: item.doEarly,
              timeMinute: item.timeMinute,
              status: item.carQueue.status,
              currentServiceArea: item.currentServiceArea,
              statusPayment: item.statusPayment,
            })
          })
        }

        state.data.viewOrderServicesDone = viewOrderServicesDoneNew
      })
      .addCase(getAllOrderServicesDone.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getOrderServiceDoneById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getOrderServiceDoneById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderServiceDone = payload
      })
      .addCase(getOrderServiceDoneById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllOrderMaintenancesDoneByOrderServiceId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderMaintenancesDoneByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceDoneByOrderServiceId = payload
        }
      )
      .addCase(
        getAllOrderMaintenancesDoneByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsDoneByOrderServiceId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsDoneByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceItemsDoneByOrderServiceId = payload
          let orderMaintenanceItemAccessoriesDoneByOrderMaintenanceItemIdNew =
            []

          if (payload.length) {
            payload.map((item) => {
              if (item.orderMaintenanceItemAccessories.length) {
                item.orderMaintenanceItemAccessories.map((accessory) => {
                  orderMaintenanceItemAccessoriesDoneByOrderMaintenanceItemIdNew.push(
                    {
                      ...accessory,
                      payment: item.payment,
                    }
                  )
                })
              }
            })
          }

          state.data.orderMaintenanceItemAccessoriesDoneByOrderMaintenanceItemId =
            orderMaintenanceItemAccessoriesDoneByOrderMaintenanceItemIdNew
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsDoneByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllOrderRepairItemsDoneByOrderServiceId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderRepairItemsDoneByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderRepairItemsDoneByOrderServiceId = payload
          let orderRepairItemAccessoriesDoneByOrderRepairItemIdNew = []
          if (payload.length) {
            payload.map((item) => {
              if (item.orderRepairItemAccessories.length) {
                item.orderRepairItemAccessories.map((accessory) => {
                  orderRepairItemAccessoriesDoneByOrderRepairItemIdNew.push({
                    ...accessory,
                    payment: item.payment,
                  })
                })
              }
            })
          }

          state.data.orderRepairItemAccessoriesDoneByOrderRepairItemId =
            orderRepairItemAccessoriesDoneByOrderRepairItemIdNew
        }
      )
      .addCase(
        getAllOrderRepairItemsDoneByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )

      .addCase(paymentOrderService.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(paymentOrderService.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.billService = payload
      })
      .addCase(paymentOrderService.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllBillServices.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllBillServices.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.billServices = payload
        const viewBillServicesNew = []
        if (payload.length) {
          payload.map((item) => {
            viewBillServicesNew.push({
              id: item.id,
              customerName: item.orderService.carQueue.fullName,
              carPlate: Helper.formatCarNumberPlate(
                item.orderService.carQueue.carNumberPlates
              ),
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              totalAmount: Helper.formatCurrencyToVND(item.totalAmount),
              totalAmountAfterTax: Helper.formatCurrencyToVND(
                item.totalAmountAfterTax
              ),
              createdAt: item.createdAt,
            })
          })
        }
        state.data.viewBillServices = viewBillServicesNew
      })
      .addCase(getAllBillServices.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getBillServiceById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getBillServiceById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.billService = payload
      })
      .addCase(getBillServiceById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getManagementCarPlateByCarPlate.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getManagementCarPlateByCarPlate.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.managementCarPlate = payload
        }
      )
      .addCase(
        getManagementCarPlateByCarPlate.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getTimeCreateOrderService.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getTimeCreateOrderService.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderServiceTimeCreate = payload
      })
      .addCase(getTimeCreateOrderService.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getTimeDoneOrderService.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getTimeDoneOrderService.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderServiceTimeDone = payload
      })
      .addCase(getTimeDoneOrderService.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllBillServiceDetailByBillServiceId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllBillServiceDetailByBillServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.billServiceDetails = payload
        }
      )
      .addCase(
        getAllBillServiceDetailByBillServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(
        getAllBillServiceDetailAccessoryByBillServiceDetailId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllBillServiceDetailAccessoryByBillServiceDetailId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.billServiceDetailAccessories = payload
        }
      )
      .addCase(
        getAllBillServiceDetailAccessoryByBillServiceDetailId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getTopFiveBillServicesRecent.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getTopFiveBillServicesRecent.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.billServiceTopFiveRecents = payload
      })
      .addCase(getTopFiveBillServicesRecent.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getRevenueByDay.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getRevenueByDay.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.revenueByDay = {}
        state.data.revenueByDay = payload
        const viewBillServicesByDayNew = []
        if (payload?.billServiceResDTOList?.length) {
          payload.billServiceResDTOList.map((item) => {
            viewBillServicesByDayNew.push({
              id: item.id,
              customerName: item.orderService.carQueue.fullName,
              carPlate: Helper.formatCarNumberPlate(
                item.orderService.carQueue.carNumberPlates
              ),
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              totalAmount: Helper.formatCurrencyToVND(item.totalAmount),
              totalAmountAfterTax: Helper.formatCurrencyToVND(
                item.totalAmountAfterTax
              ),
              createdAt: item.createdAt,
            })
          })
        }
        state.data.viewBillServicesByDay = viewBillServicesByDayNew
      })
      .addCase(getRevenueByDay.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getRevenueByMonth.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getRevenueByMonth.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.revenueByMonth = {}
        state.data.revenueByMonth = payload
        const viewBillServicesByMonth = []
        if (payload?.billServiceResDTOList?.length) {
          payload.billServiceResDTOList.map((item) => {
            viewBillServicesByMonth.push({
              id: item.id,
              customerName: item.orderService.carQueue.fullName,
              carPlate: Helper.formatCarNumberPlate(
                item.orderService.carQueue.carNumberPlates
              ),
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              totalAmount: Helper.formatCurrencyToVND(item.totalAmount),
              totalAmountAfterTax: Helper.formatCurrencyToVND(
                item.totalAmountAfterTax
              ),
              createdAt: item.createdAt,
            })
          })
        }
        state.data.viewBillServicesByMonth = viewBillServicesByMonth
      })
      .addCase(getRevenueByMonth.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getRevenueByYear.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getRevenueByYear.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.revenueByYear = {}
        state.data.revenueByYear = payload
        const viewBillServicesByYearNew = []
        if (payload?.billServiceResDTOList?.length) {
          payload.billServiceResDTOList.map((item) => {
            viewBillServicesByYearNew.push({
              id: item.id,
              customerName: item.orderService.carQueue.fullName,
              carPlate: Helper.formatCarNumberPlate(
                item.orderService.carQueue.carNumberPlates
              ),
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              totalAmount: Helper.formatCurrencyToVND(item.totalAmount),
              totalAmountAfterTax: Helper.formatCurrencyToVND(
                item.totalAmountAfterTax
              ),
              createdAt: item.createdAt,
            })
          })
        }
        state.data.viewBillServicesByYear = viewBillServicesByYearNew
      })
      .addCase(getRevenueByYear.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getRevenueByPeriod.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getRevenueByPeriod.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.revenueByPeriod = {}
        state.data.revenueByPeriod = payload
        const viewBillServicesByPeriodNew = []
        if (payload?.billServiceResDTOList?.length) {
          payload.billServiceResDTOList.map((item) => {
            viewBillServicesByPeriodNew.push({
              id: item.id,
              customerName: item.orderService.carQueue.fullName,
              carPlate: Helper.formatCarNumberPlate(
                item.orderService.carQueue.carNumberPlates
              ),
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              totalAmount: Helper.formatCurrencyToVND(item.totalAmount),
              totalAmountAfterTax: Helper.formatCurrencyToVND(
                item.totalAmountAfterTax
              ),
              createdAt: item.createdAt,
            })
          })
        }
        state.data.viewBillServicesByPeriod = viewBillServicesByPeriodNew
      })
      .addCase(getRevenueByPeriod.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer, actions } = carSlice
export const { setAllOrderServicesDone } = actions

export default reducer
