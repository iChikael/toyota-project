import { SOCKET_SERVER } from 'constants/global'
import socketIOClient from 'socket.io-client'

let socket

const socketApi = {
  connectServer: () => {
    socket = socketIOClient(SOCKET_SERVER)
    socket.emit('create-room', 'toyota')
    return socket
  },
}

export default socketApi
