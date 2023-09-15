import { useState } from 'react'
import Box from '@mui/material/Box'
import Stepper from '@mui/material/Stepper'
import Step from '@mui/material/Step'
import StepLabel from '@mui/material/StepLabel'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'
import OrderServiceDetailTechnical from './components/OrderServiceDetailTechnical'
import ListServiceArea from './components/ListServiceArea'
import { Grid } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import NextButton from 'components/Button/NextButton'
import BackButton from 'components/Button/BackButton'
import BackListButton from 'components/Button/BackListButton'

const steps = ['Chi tiết dịch vụ', 'Khu vực sửa chữa']

const OrderServiceTechnicalStepper = () => {
  const navigate = useNavigate()
  const goBack = () => {
    navigate(-1)
  }

  const [activeStep, setActiveStep] = useState(0)

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1)
  }

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1)
  }

  return (
    <>
      <Box display="flex" justifyContent="end" mr={'50px'} mt={'10px'}>
        <BackListButton
          text="Danh sách lệnh sửa chữa, bảo dưỡng"
          onClick={goBack}
        />
      </Box>
      <Box sx={{ width: '100%', mt: '30px' }}>
        <Stepper activeStep={activeStep} sx={{ color: 'red' }}>
          {steps.map((label, index) => {
            const stepProps = {}
            const labelProps = {}

            return (
              <Step key={label} {...stepProps} sx={{ fontSize: '1rem' }}>
                <StepLabel {...labelProps}>{label}</StepLabel>
              </Step>
            )
          })}
        </Stepper>

        <Typography sx={{ mt: 2, mb: 1 }}>
          {activeStep + 1 === 1 ? (
            <OrderServiceDetailTechnical />
          ) : (
            <Grid container md={11} mx={5}>
              <Grid
                item
                md={12}
                container
                spacing={1}
                direction="row"
                justify="flex-start"
                alignItems="flex-start"
              >
                <ListServiceArea />
              </Grid>
            </Grid>
          )}
        </Typography>
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'row',
            pt: 2,
            padding: '0 15px',
            fontSize: '1rem',
          }}
        >
          {activeStep === 0 ? <></> : <BackButton onClick={handleBack} />}

          <Box sx={{ flex: '1 1 auto', fontSize: '1rem' }} />
          {activeStep === steps.length - 1 ? (
            <></>
          ) : (
            <Box display="flex" justifyContent="end" mr={'50px'}>
              <NextButton onClick={handleNext} />
            </Box>
          )}
        </Box>
      </Box>
    </>
  )
}
export default OrderServiceTechnicalStepper
