import React, { Suspense, useState } from 'react'
import { Routes, Route, BrowserRouter } from 'react-router-dom'

import { CssBaseline, ThemeProvider } from '@mui/material'
import { ColorModeContext, useMode } from './theme'

// import Dashboard from './scenes/dashboard'
import Team from './scenes/team'
import Invoices from './scenes/invoices'
import Contacts from './scenes/contacts'
import Bar from './scenes/bar'
import Form from './scenes/form'
import Line from './scenes/line'
import Pie from './scenes/pie'
import FAQ from './scenes/faq'
import Geography from './scenes/geography'
import Calendar from './scenes/calendar/calendar'

import { EnumUserRole } from 'constants/EnumUserRole'

import Layout from 'components/Layout'
import RequireAuth from 'RequireAuth'

import ListOrderService from 'features/Technical/components/ListOrderService'
import OrderServiceDetailUseGeneralRepairArea from 'features/Technical/GeneralRepairParts/components/OrderServiceDetailUseGeneralRepairParts'
import RefrigerationArea from 'features/Technical/RefrigerationArea'
import UndercarriageArea from 'features/Technical/UndercarriageArea'
import GeneralRepairArea from 'features/Technical/GeneralRepairParts'
import CoPaintedArea from 'features/Technical/CoPaintedParts'
import WashArea from 'features/Technical/WashArea'
import OrderServiceDetailUseCoPaintedArea from 'features/Technical/CoPaintedParts/components/OrderServiceDetailUseCoPaintedArea'
import OrderServiceDetailUseUndercarriageArea from 'features/Technical/UndercarriageArea/components/OrderServiceDetailUseUndercarriageArea'
import OrderServiceDetailUseRefrigerationArea from 'features/Technical/RefrigerationArea/components/OrderServiceDetailUseRefrigerationArea'
import OrderServiceDetailUseWashArea from 'features/Technical/WashArea/components/OrderServiceDetailUseWashArea'
import OrderServiceTechnicalStepper from 'features/Technical/OrderServiceDetail'

import CashierPage from 'features/Cashier'
import OrderServicePayment from 'features/Cashier/components/OrderServicePayment'
import ListBillService from 'features/Cashier/components/ListBillService'
import BillServiceDetail from 'features/Cashier/components/BillServiceDetail'

import ListCarQueuePage from 'features/CarQueue/List'
import CreateCarQueuePage from 'features/CarQueue/Create'
import ManagementCarPlatePage from 'features/Reception/ManagementCarPlatePage'
import CreateCarPlatePage from 'features/Reception/ManagementCarPlatePage/components/CreateCarPlatePage'
import CreateOrderServicePage from 'features/Reception/CreateOrderServicePage'
import OrderServiceDetailPage from 'features/Reception/CreateOrderServicePage/components/OrderServiceDetailPage'
import UpdateOrderServicePage from 'features/Reception/CreateOrderServicePage/components/UpdateOrderServicePage'
import RevenuePage from 'features/Cashier/RevenuePage'

import DashboardPage from 'features/Dashboard'

const NotFound = React.lazy(() => import('components/NotFound'))
const Unauthorized = React.lazy(() => import('components/Unauthorized'))

const LoginPage = React.lazy(() => import('features/Login'))
// const CarQueueCreatePage = React.lazy(() => import('features/CarQueue/Create'))
// const CarQueueListPage = React.lazy(() => import('features/CarQueue/List'))

const AccessoryListPage = React.lazy(() => import('features/Accessory/List'))
const CreateAccessoryPage = React.lazy(() =>
  import('features/Accessory/Create')
)

const StaffListPage = React.lazy(() => import('features/Staff/List'))
const StaffRegisterPage = React.lazy(() => import('features/Staff/Register'))

const CustomerListPage = React.lazy(() => import('features/Customer/List'))
const CustomerRegisterPage = React.lazy(() =>
  import('features/Customer/Register')
)

const CarListPage = React.lazy(() => import('features/Car/List'))
const CarCreatePage = React.lazy(() => import('features/Car/Create'))

const RepairItemListPage = React.lazy(() => import('features/RepairItem/List'))
const RepairItemCreatePage = React.lazy(() =>
  import('features/RepairItem/Create')
)

const ServiceAreaListPage = React.lazy(() =>
  import('features/ServiceArea/List')
)
const ServiceAreaCreatePage = React.lazy(() =>
  import('features/ServiceArea/Create')
)

function App() {
  const [theme, colorMode] = useMode()
  const [isSidebar, setIsSidebar] = useState(true)

  return (
    <>
      <Suspense fallback={<div>Loading ...</div>}>
        <BrowserRouter>
          <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
              <CssBaseline />
              <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route
                  element={
                    <RequireAuth
                      allowedRoles={[
                        EnumUserRole.ROLE_ADMIN,
                        EnumUserRole.ROLE_MANAGER,
                        EnumUserRole.ROLE_CASHIER,
                        EnumUserRole.ROLE_RECEPTION,
                        EnumUserRole.ROLE_TECHNICAL,
                        EnumUserRole.ROLE_WAREHOUSE,
                      ]}
                    />
                  }
                >
                  <Route path="/" element={<Layout />}>
                    <Route path="/" element={<DashboardPage />} />
                    <Route path="/unauthorized" element={<Unauthorized />} />
                    <Route path="/staffs" element={<StaffListPage />} />
                    <Route
                      path="/staffs/create"
                      element={<StaffRegisterPage />}
                    />
                    <Route path="/customers" element={<CustomerListPage />} />
                    <Route
                      path="/customers/create"
                      element={<CustomerRegisterPage />}
                    />
                    <Route path="/team" element={<Team />} />
                    <Route path="/contacts" element={<Contacts />} />
                    <Route path="/invoices" element={<Invoices />} />
                    <Route path="/form" element={<Form />} />
                    <Route
                      path="/accessories"
                      element={<AccessoryListPage />}
                    />
                    <Route
                      path="/accessories/create"
                      element={<CreateAccessoryPage />}
                    />
                    <Route
                      path="/repair-items"
                      element={<RepairItemListPage />}
                    />
                    <Route
                      path="/repair-items/create"
                      element={<RepairItemCreatePage />}
                    />
                    <Route
                      path="/service-area"
                      element={<ServiceAreaListPage />}
                    />
                    <Route
                      path="/service-area/create"
                      element={<ServiceAreaCreatePage />}
                    />
                    <Route path="/cars" element={<CarListPage />} />
                    <Route path="/cars/create" element={<CarCreatePage />} />
                    <Route
                      path="/reception/car-queues"
                      element={<ListCarQueuePage />}
                    />
                    <Route
                      path="/reception/car-queues/create"
                      element={<CreateCarQueuePage />}
                    />
                    <Route
                      path="/reception/car-plates"
                      element={<ManagementCarPlatePage />}
                    />
                    <Route
                      path="/reception/car-plates/create"
                      element={<CreateCarPlatePage />}
                    />
                    <Route
                      path="/reception/order-services/create"
                      element={<CreateOrderServicePage />}
                    />
                    <Route
                      path="/reception/order-services/detail"
                      element={<OrderServiceDetailPage />}
                    />
                    <Route
                      path="/reception/order-services/update"
                      element={<UpdateOrderServicePage />}
                    />
                    <Route
                      path="/technical/order-services"
                      element={<ListOrderService />}
                    />
                    <Route
                      path="/technical/order-services/:orderId"
                      element={<OrderServiceTechnicalStepper />}
                    />
                    <Route
                      path="/technical/service-areas/co-paint-area"
                      element={<CoPaintedArea />}
                    />
                    <Route
                      path="/technical/service-area/co-paint-area/order-services/:orderId"
                      element={<OrderServiceDetailUseCoPaintedArea />}
                    />
                    <Route
                      path="/technical/service-areas/general-repair-area"
                      element={<GeneralRepairArea />}
                    />
                    <Route
                      path="/technical/service-area/general-repair-area/order-services/:orderId"
                      element={<OrderServiceDetailUseGeneralRepairArea />}
                    />
                    <Route
                      path="/technical/service-areas/undercarriage-area"
                      element={<UndercarriageArea />}
                    />
                    <Route
                      path="/technical/service-area/undercarriage-area/order-services/:orderId"
                      element={<OrderServiceDetailUseUndercarriageArea />}
                    />
                    <Route
                      path="/technical/service-areas/refrigeration-area"
                      element={<RefrigerationArea />}
                    />
                    <Route
                      path="/technical/service-area/refrigeration-area/order-services/:orderId"
                      element={<OrderServiceDetailUseRefrigerationArea />}
                    />
                    <Route
                      path="/technical/service-areas/wash-area"
                      element={<WashArea />}
                    />
                    <Route
                      path="/technical/service-area/wash-area/order-services/:orderId"
                      element={<OrderServiceDetailUseWashArea />}
                    />
                    <Route
                      path="/cashier/order-services-done"
                      element={<CashierPage />}
                    />
                    <Route
                      path="/cashier/order-services-done/:orderServiceId"
                      element={<OrderServicePayment />}
                    />
                    <Route
                      path="/cashier/bill-services"
                      element={<ListBillService />}
                    />
                    <Route
                      path="/cashier/bill-services/:billServiceId"
                      element={<BillServiceDetail />}
                    />
                    <Route path="/cashier/revenues" element={<RevenuePage />} />
                    <Route path="/bar" element={<Bar />} />
                    <Route path="/pie" element={<Pie />} />
                    <Route path="/line" element={<Line />} />s
                    <Route path="/faq" element={<FAQ />} />
                    <Route path="/calendar" element={<Calendar />} />
                    <Route path="/geography" element={<Geography />} />
                    <Route path="*" element={<NotFound />} />
                  </Route>
                </Route>
              </Routes>
            </ThemeProvider>
          </ColorModeContext.Provider>
        </BrowserRouter>
      </Suspense>
    </>
  )
}

export default App
