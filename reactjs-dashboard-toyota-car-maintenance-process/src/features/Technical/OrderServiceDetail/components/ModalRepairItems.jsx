import React from 'react'
import { useSelector } from 'react-redux'

import {
  Box,
  Button,
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

const style = {
  position: 'relative',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '75%',
  height: '75%',
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

const ModalRepairItems = ({
  openModalRepairItems,
  handleCloseModalRepairItems,
}) => {
  const orderRepairItems = useSelector(
    (state) => state.technical.data.orderRepairItemsByOrderServiceId
  )
  return (
    <div>
      <Modal
        open={openModalRepairItems}
        onClose={handleCloseModalRepairItems}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        className={modalStyle}
        overflow="scroll"
      >
        <Box sx={style}>
          <Typography
            display={'flex'}
            alignItems={'center'}
            justifyContent={'center'}
            variant="h4"
            // color={colors.grey[100]}
          >
            Hạng mục sửa chữa
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
                    HẠNG MỤC DỊCH VỤ
                  </TableCell>
                  <TableCell component="th" align="center">
                    THỰC HIỆN
                  </TableCell>
                  <TableCell component="th" align="center">
                    TRẠNG THÁI
                  </TableCell>
                  <TableCell component="th" align="center">
                    KHU VỰC
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {orderRepairItems &&
                  orderRepairItems.map((item, index) => {
                    return (
                      <TableRow key={index}>
                        <TableCell component="th" align="center">
                          {item.id}
                        </TableCell>
                        <TableCell component="th">{item.name}</TableCell>
                        <TableCell component="th" align="center">
                          Thay thế, sửa chữa
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.status}
                        </TableCell>
                        <TableCell component="th" align="center">
                          {item.repairItem.serviceArea.name}
                        </TableCell>
                      </TableRow>
                    )
                  })}
              </TableBody>
            </Table>
          </TableContainer>
          <br />
          <Box display={'flex'} justifyContent={'end'}>
            <Button
              variant="contained"
              type="button"
              color="secondary"
              onClick={handleCloseModalRepairItems}
              sx={{ display: 'flex', justifyContent: 'end' }}
            >
              Đóng
            </Button>
          </Box>
        </Box>
      </Modal>
    </div>
  )
}

export default ModalRepairItems
