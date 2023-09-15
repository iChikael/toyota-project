import { useState } from 'react'
import { ProSidebar, Menu, MenuItem } from 'react-pro-sidebar'
import { Box, IconButton, Typography, useTheme } from '@mui/material'
import { Link } from 'react-router-dom'
import 'react-pro-sidebar/dist/css/styles.css'
import { tokens } from 'theme'
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined'
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined'
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined'
import { EnumUserRole } from 'constants/EnumUserRole'
import { useSelector } from 'react-redux'

import PrecisionManufacturingIcon from '@mui/icons-material/PrecisionManufacturing'
import CarRepairIcon from '@mui/icons-material/CarRepair'
import TireRepairIcon from '@mui/icons-material/TireRepair'
import FormatPaintIcon from '@mui/icons-material/FormatPaint'
import ElectricRickshawIcon from '@mui/icons-material/ElectricRickshaw'
import LocalCarWashIcon from '@mui/icons-material/LocalCarWash'
import ViewAgendaIcon from '@mui/icons-material/ViewAgenda'
import QueueIcon from '@mui/icons-material/Queue'
import Inventory2Icon from '@mui/icons-material/Inventory2'
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline'
import ReceiptIcon from '@mui/icons-material/Receipt'
import PriceCheckIcon from '@mui/icons-material/PriceCheck'
import GridViewIcon from '@mui/icons-material/GridView'
import ConstructionIcon from '@mui/icons-material/Construction'
import DriveEtaIcon from '@mui/icons-material/DriveEta'
import BadgeIcon from '@mui/icons-material/Badge'
import LocalAtmIcon from '@mui/icons-material/LocalAtm'

const Item = ({ title, to, icon, selected, setSelected }) => {
  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  return (
    <MenuItem
      active={selected === title}
      style={{
        color: colors.grey[100],
      }}
      onClick={() => setSelected(title)}
      icon={icon}
    >
      <Typography>{title}</Typography>
      <Link to={to} />
    </MenuItem>
  )
}

const Sidebar = () => {
  const auth = useSelector((state) => state.auth.data)
  const role = auth.role
  const fullName = auth.fullName
  const sub = auth.sub

  const theme = useTheme()
  const colors = tokens(theme.palette.mode)
  const [isCollapsed, setIsCollapsed] = useState(false)
  const [selected, setSelected] = useState('Dashboard')

  const authManager =
    Object.values(EnumUserRole).find((item) => item === role) ===
    EnumUserRole.ROLE_ADMIN

  const authReception =
    Object.values(EnumUserRole).find((item) => item === role) ===
    EnumUserRole.ROLE_RECEPTION

  const authCashier =
    Object.values(EnumUserRole).find((item) => item === role) ===
    EnumUserRole.ROLE_CASHIER

  const authTechnical =
    Object.values(EnumUserRole).find((item) => item === role) ===
    EnumUserRole.ROLE_TECHNICAL

  const authWareHouse =
    Object.values(EnumUserRole).find((item) => item === role) ===
    EnumUserRole.ROLE_WAREHOUSE

  return (
    <Box
      sx={{
        '& .pro-sidebar-inner': {
          background: `${colors.primary[400]} !important`,
        },
        '& .pro-icon-wrapper': {
          backgroundColor: 'transparent !important',
        },
        '& .pro-inner-item': {
          padding: '5px 35px 5px 20px !important',
        },
        '& .pro-inner-item:hover': {
          color: '#868dfb !important',
        },
        '& .pro-menu-item.active': {
          color: '#6870fa !important',
        },
      }}
    >
      <ProSidebar collapsed={isCollapsed}>
        <Menu iconShape="square">
          {/* LOGO AND MENU ICON */}
          <MenuItem
            onClick={() => setIsCollapsed(!isCollapsed)}
            icon={isCollapsed ? <MenuOutlinedIcon /> : undefined}
            style={{
              margin: '10px 0 20px 0',
              color: colors.grey[100],
            }}
          >
            {!isCollapsed && (
              <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                ml="15px"
              >
                <Typography variant="h3" color={colors.grey[100]}>
                  DASHBOARD
                </Typography>
                <IconButton onClick={() => setIsCollapsed(!isCollapsed)}>
                  <MenuOutlinedIcon />
                </IconButton>
              </Box>
            )}
          </MenuItem>

          {!isCollapsed && (
            <Box mb="25px">
              <Box display="flex" justifyContent="center" alignItems="center">
                <img
                  alt="profile-user"
                  width="100px"
                  height="100px"
                  src={`/assets/bob.jpg`}
                  style={{ cursor: 'pointer', borderRadius: '50%' }}
                />
              </Box>
              <Box textAlign="center">
                <Typography
                  variant="h2"
                  color={colors.grey[100]}
                  fontWeight="bold"
                  sx={{ m: '10px 0 0 0', fontSize: '18px' }}
                >
                  {fullName}
                </Typography>
                <Typography variant="h5" color={colors.greenAccent[500]}>
                  {role}
                </Typography>
              </Box>
            </Box>
          )}

          <Box paddingLeft={isCollapsed ? undefined : '2%'}>
            {authManager && (
              <>
                <Item
                  title="Dashboard"
                  to="/"
                  icon={<HomeOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Quản lý
                </Typography>
                <Item
                  title="Danh sách nhân viên"
                  to="/staffs"
                  icon={<BadgeIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Danh sách xe"
                  to="/cars"
                  icon={<DriveEtaIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Danh sách khách hàng"
                  to="/customers"
                  icon={<BadgeIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                {/* <Item
                  title="Thêm mới khách hàng"
                  to="/customers/create"
                  icon={<BadgeIcon />}
                  selected={selected}
                  setSelected={setSelected}
                /> */}
                <Item
                  title="Hạng mục sửa chữa "
                  to="/repair-items"
                  icon={<ConstructionIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Khu vực sửa chữa "
                  to="/service-area"
                  icon={<GridViewIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                {/* <Item
                  title="Thêm khu vực sửa chữa "
                  to="/service-area/create"
                  icon={<PersonOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                /> */}
              </>
            )}

            {(authReception || authManager) && (
              <>
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Lễ tân
                </Typography>
                {/* <Item
                  title="Tạo mới lệnh sửa chửa"
                  to="/reception/order-services/create"
                  icon={<PeopleOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                /> */}
                <Item
                  title="Quản lý biển số xe"
                  to="/reception/car-plates"
                  icon={<ViewAgendaIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />

                <Item
                  title="Danh sách xe hàng chờ "
                  to="/reception/car-queues"
                  icon={<QueueIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
              </>
            )}

            {(authManager || authCashier) && (
              <>
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Thu ngân
                </Typography>

                <Item
                  title="Xe chờ thanh toán"
                  to="/cashier/order-services-done"
                  icon={<PriceCheckIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Danh sách hóa đơn"
                  to="/cashier/bill-services"
                  icon={<ReceiptIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Thống kê doanh thu"
                  to="/cashier/revenues"
                  icon={<LocalAtmIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
              </>
            )}

            {(authManager || authTechnical) && (
              <>
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Kỹ thuật
                </Typography>
                <Item
                  title="Danh sách lệnh sửa chữa"
                  to="/technical/order-services"
                  icon={<PrecisionManufacturingIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Khu vực đồng sơn"
                  to="/technical/service-areas/co-paint-area"
                  icon={<FormatPaintIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Khu vực sửa chữa chung"
                  to="/technical/service-areas/general-repair-area"
                  icon={<TireRepairIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Khu vực gầm máy"
                  to="/technical/service-areas/undercarriage-area"
                  icon={<CarRepairIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Khu vực điện, điện lạnh"
                  to="/technical/service-areas/refrigeration-area"
                  icon={<ElectricRickshawIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Khu vực rửa xe"
                  to="/technical/service-areas/wash-area"
                  icon={<LocalCarWashIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
              </>
            )}

            {(authWareHouse || authManager) && (
              <>
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Kho
                </Typography>
                <Item
                  title="Quản lý linh kiện"
                  to="/accessories"
                  icon={<Inventory2Icon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Thêm mới linh kiện"
                  to="/accessories/create"
                  icon={<AddCircleOutlineIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
              </>
            )}

            {/* <Item
              title="Calendar"
              to="/calendar"
              icon={<CalendarTodayOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            />
            <Item
              title="FAQ Page"
              to="/faq"
              icon={<HelpOutlineOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            /> */}

            {/* <Typography
              variant="h6"
              color={colors.grey[300]}
              sx={{ m: '15px 0 5px 20px' }}
            >
              Charts
            </Typography>
            <Item
              title="Bar Chart"
              to="/bar"
              icon={<BarChartOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            />
            <Item
              title="Pie Chart"
              to="/pie"
              icon={<PieChartOutlineOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            />
            <Item
              title="Line Chart"
              to="/line"
              icon={<TimelineOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            />
            <Item
              title="Geography Chart"
              to="/geography"
              icon={<MapOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            /> */}
          </Box>
        </Menu>
      </ProSidebar>
    </Box>
  )
}

export default Sidebar
