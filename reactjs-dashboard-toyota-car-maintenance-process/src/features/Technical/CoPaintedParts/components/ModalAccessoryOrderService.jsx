import {
  Box,
  Button,
  Fade,
  Modal,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'
import React from 'react'
import { useSelector } from 'react-redux'

const style = {
  position: 'relative',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '50%',
  height: '50%',
  overflow: 'scroll',
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
  display: 'block',
}
const modalStyle = {
  position: 'absolute',
  top: '10%',
  left: '10%',
  height: '100%',
}

const ModalAccessoryOrderService = ({
  openModalAccessory,
  handleCloseModalAccessory,
}) => {
  const accessoryByServiceItems = useSelector(
    (state) => state.technical.data.accessoryByServiceItems
  )

  return (
    <Modal
      open={openModalAccessory}
      onclose={handleCloseModalAccessory}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      className={modalStyle}
      overflow="scroll"
    >
      <Fade in={openModalAccessory}>
        <Box sx={style}>
          <Typography
            display={'flex'}
            alignItems={'center'}
            justifyContent={'center'}
            variant="h4"
          >
            Vật tư đính kèm
          </Typography>
          <br />
          <TableContainer component={Paper}>
            <Table
              sx={{ minWidth: 650, height: 'max-content' }}
              aria-label="simple txable"
              border={1}
            >
              <TableHead>
                <TableRow>
                  <TableCell component="th" align="center">
                    MÃ SỐ
                  </TableCell>
                  <TableCell component="th" align="center">
                    TÊN VẬT TƯ
                  </TableCell>
                  <TableCell component="th" align="center">
                    ĐVT
                  </TableCell>
                  <TableCell component="th" align="center">
                    SỐ LƯỢNG
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {accessoryByServiceItems &&
                  accessoryByServiceItems.orderMaintenanceItemAccessories &&
                  accessoryByServiceItems.orderMaintenanceItemAccessories.map(
                    (item, index) => {
                      return (
                        <TableRow key={index}>
                          <TableCell component="th" align="center">
                            {item.accessory.code}
                          </TableCell>
                          <TableCell component="th">
                            {item.accessoryName}
                          </TableCell>
                          <TableCell component="th" align="center">
                            {item.accessoryUnit}
                          </TableCell>
                          <TableCell component="th" align="center">
                            {item.accessoryQuantity}
                          </TableCell>
                        </TableRow>
                      )
                    }
                  )}
                {accessoryByServiceItems &&
                  accessoryByServiceItems.orderRepairItemAccessories &&
                  accessoryByServiceItems.orderRepairItemAccessories.map(
                    (item, index) => {
                      return (
                        <TableRow key={index}>
                          <TableCell component="th" align="center">
                            {item.accessory.code}
                          </TableCell>
                          <TableCell component="th">
                            {item.accessoryName}
                          </TableCell>
                          <TableCell component="th" align="center">
                            {item.accessoryUnit}
                          </TableCell>
                          <TableCell component="th" align="center">
                            {item.accessoryQuantity}
                          </TableCell>
                        </TableRow>
                      )
                    }
                  )}
              </TableBody>
            </Table>
          </TableContainer>
          <br />
          <Box display={'flex'} justifyContent={'end'}>
            <Button
              variant="contained"
              type="button"
              color="secondary"
              onClick={handleCloseModalAccessory}
              sx={{ display: 'flex', justifyContent: 'end' }}
            >
              Close
            </Button>
          </Box>
        </Box>
      </Fade>
    </Modal>
  )
}

export default ModalAccessoryOrderService
