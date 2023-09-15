import React, { useState } from 'react'
import RevenueByYearPage from './RevenueByYearPage'
import { Box, Button, Grid, Menu } from '@mui/material'
import Header from 'components/Header'
import { useNavigate } from 'react-router-dom'
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import { MenuItem } from 'react-pro-sidebar'
import dayjs from 'dayjs'

import styles from './components/RevenuePage.module.scss'
import { useDispatch } from 'react-redux'
import RevenueByDayPage from './RevenueByDayPage'
import RevenueByMonthPage from './RevenueByMonthPage'
import RevenueByPeriodPage from './RevenueByPeriodPage'
import { ToastContainer } from 'react-toastify'

const RevenuePage = () => {
  let date = new Date()

  const [anchorElByDate, setAnchorElByDate] = useState(null)

  const [day, setDay] = useState('')
  const [loadedRevenueByDay, setLoadedRevenueByDay] = useState(false)
  const handleClickRevenueByDay = (event) => {
    setAnchorElByDate(event.currentTarget)
  }

  const handleChangeDay = (e) => {
    if (e) {
      setDay(dayjs(e.$d).format('YYYY-MM-DD'))
      if (dayjs(e.$d).format('YYYY-MM-DD') !== '') {
        setLoadedRevenueByDay(true)
        setLoadedRevenueByYear(false)
        setLoadedRevenueByMonth(false)
        setLoadedRevenueByPeriod(false)
      } else {
        setLoadedRevenueByDay(false)
      }
    }
  }
  const handleCloseRevenueByDay = () => {
    setAnchorElByDate(null)
  }

  const [anchorElByMonth, setAnchorElByMonth] = useState(null)

  const [month, setMonth] = useState('')
  const [loadedRevenueByMonth, setLoadedRevenueByMonth] = useState(false)
  const handleClickRevenueByMonth = (event) => {
    setAnchorElByMonth(event.currentTarget)
  }

  const handleChangeMonth = (e) => {
    if (e) {
      setMonth(dayjs(e.$d).format('YYYY-MM'))
      if (dayjs(e.$d).format('YYYY-MM') !== '') {
        setLoadedRevenueByMonth(true)
        setLoadedRevenueByYear(false)
        setLoadedRevenueByDay(false)
        setLoadedRevenueByPeriod(false)
      } else {
        setLoadedRevenueByMonth(false)
      }
    }
  }
  const handleCloseRevenueByMonth = () => {
    setAnchorElByMonth(null)
  }

  const [anchorElByYear, setAnchorElByYear] = useState(null)

  const [year, setYear] = useState('')
  const [loadedRevenueByYear, setLoadedRevenueByYear] = useState(true)
  const handleClickRevenueByYear = (event) => {
    setAnchorElByYear(event.currentTarget)
  }

  const handleChangeYear = (e) => {
    if (e) {
      setYear(dayjs(e.$d).format('YYYY'))
      if (dayjs(e.$d).format('YYYY') !== '') {
        setLoadedRevenueByYear(true)
        setLoadedRevenueByDay(false)
        setLoadedRevenueByMonth(false)
        setLoadedRevenueByPeriod(false)
      } else {
        setLoadedRevenueByYear(false)
      }
    }
  }

  const handleCloseRevenueByYear = () => {
    setAnchorElByYear(null)
  }

  const [anchorElByPeriod, setAnchorElByPeriod] = useState(null)

  const [period, setPeriod] = useState({
    startDate: '',
    endDate: '',
  })
  const [loadedRevenueByPeriod, setLoadedRevenueByPeriod] = useState(false)
  const handleClickRevenueByPeriod = (event) => {
    setAnchorElByPeriod(event.currentTarget)
  }

  const handleChangeStartDate = (e) => {
    if (e) {
      setPeriod({ ...period, startDate: dayjs(e.$d).format('YYYY-MM-DD') })
      if (dayjs(e.$d).format('YYYY-MM-DD') !== '') {
        setLoadedRevenueByPeriod(true)
        setLoadedRevenueByDay(false)
        setLoadedRevenueByMonth(false)
        setLoadedRevenueByYear(false)
      } else {
        setLoadedRevenueByPeriod(false)
      }
    }
  }

  const handleChangeEndDate = (e) => {
    if (e) {
      setPeriod({ ...period, endDate: dayjs(e.$d).format('YYYY-MM-DD') })
      if (dayjs(e.$d).format('YYYY-MM-DD') !== '') {
        setLoadedRevenueByPeriod(true)
        setLoadedRevenueByDay(false)
        setLoadedRevenueByMonth(false)
        setLoadedRevenueByYear(false)
      } else {
        setLoadedRevenueByPeriod(false)
      }
    }
  }

  const handleCloseRevenueByPeriod = () => {
    setAnchorElByPeriod(null)
  }

  return (
    <Box m={'20px'}>
      <ToastContainer />
      <Grid
        item
        md={12}
        sx={{ display: 'flex', justifyContent: 'space-between' }}
      >
        <Header title="Doanh thu theo đơn sửa chữa, bảo dưỡng" />
        <Box>
          <Button
            variant="contained"
            type="button"
            id="basic-button"
            aria-controls={anchorElByDate ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={anchorElByDate ? 'true' : undefined}
            onClick={handleClickRevenueByDay}
            color="secondary"
            sx={{ mb: '20px' }}
          >
            Theo ngày
          </Button>
          <Menu
            id="basic-menu"
            anchorEl={anchorElByDate}
            open={anchorElByDate ? true : false}
            onClose={handleCloseRevenueByDay}
            MenuListProps={{
              'aria-labelledby': 'basic-button',
            }}
            sx={{ margin: '10px 0 0 0' }}
          >
            <MenuItem className={styles.menuRevenue}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DatePicker']}>
                  <DatePicker onChange={(e) => handleChangeDay(e)} />
                </DemoContainer>
              </LocalizationProvider>
            </MenuItem>
          </Menu>

          <Button
            variant="contained"
            type="button"
            id="basic-button"
            aria-controls={anchorElByMonth ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={anchorElByMonth ? 'true' : undefined}
            onClick={handleClickRevenueByMonth}
            color="secondary"
            sx={{ ml: '10px', mb: '20px' }}
          >
            Theo tháng
          </Button>
          <Menu
            id="basic-menu"
            anchorEl={anchorElByMonth}
            open={anchorElByMonth ? true : false}
            onClose={handleCloseRevenueByMonth}
            MenuListProps={{
              'aria-labelledby': 'basic-button',
            }}
            sx={{ margin: '10px 0 0 0' }}
          >
            <MenuItem className={styles.menuRevenue}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DatePicker']}>
                  <DatePicker
                    views={['month']}
                    onChange={(e) => handleChangeMonth(e)}
                  />
                </DemoContainer>
              </LocalizationProvider>
            </MenuItem>
          </Menu>

          <Button
            variant="contained"
            type="button"
            id="basic-button"
            aria-controls={anchorElByYear ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={anchorElByYear ? 'true' : undefined}
            onClick={handleClickRevenueByYear}
            color="secondary"
            sx={{ ml: '10px', mb: '20px' }}
          >
            Theo năm
          </Button>
          <Menu
            id="basic-menu"
            anchorEl={anchorElByYear}
            open={anchorElByYear ? true : false}
            onClose={handleCloseRevenueByYear}
            MenuListProps={{
              'aria-labelledby': 'basic-button',
            }}
            sx={{ margin: '10px 0 0 0' }}
          >
            <MenuItem className={styles.menuRevenue}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DatePicker']}>
                  <DatePicker
                    views={['year']}
                    onChange={(e) => handleChangeYear(e)}
                  />
                </DemoContainer>
              </LocalizationProvider>
            </MenuItem>
          </Menu>

          <Button
            variant="contained"
            type="button"
            id="basic-button"
            aria-controls={anchorElByPeriod ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={anchorElByPeriod ? 'true' : undefined}
            onClick={handleClickRevenueByPeriod}
            color="secondary"
            sx={{ ml: '10px', mb: '20px' }}
          >
            Theo khoảng thời gian
          </Button>
          <Menu
            id="basic-menu"
            anchorEl={anchorElByPeriod}
            open={anchorElByPeriod ? true : false}
            onClose={handleCloseRevenueByPeriod}
            MenuListProps={{
              'aria-labelledby': 'basic-button',
            }}
            sx={{ margin: '10px 0 0 0' }}
          >
            <MenuItem className={styles.menuRevenue}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DatePicker', 'DatePicker']}>
                  <DatePicker onChange={(e) => handleChangeStartDate(e)} />
                  <DatePicker onChange={(e) => handleChangeEndDate(e)} />
                </DemoContainer>
              </LocalizationProvider>
            </MenuItem>
          </Menu>
        </Box>
      </Grid>
      {loadedRevenueByDay && day?.length && <RevenueByDayPage day={day} />}
      {loadedRevenueByMonth && month?.length && (
        <RevenueByMonthPage month={month} />
      )}
      {loadedRevenueByYear && year?.length ? (
        <RevenueByYearPage year={year} />
      ) : (
        loadedRevenueByYear && <RevenueByYearPage year={date.getFullYear()} />
      )}
      {loadedRevenueByPeriod && <RevenueByPeriodPage period={period} />}
    </Box>
  )
}

export default RevenuePage
