const express = require('express');
const http = require('http'); 
const multer = require('multer');
const path = require('path');
const fs = require('fs');
const cors = require('cors');
const app = express();
const port = 3000;
const { getHotels, addHotel,editHotel,deleteHotel } = require('./controller/hotelController');
const { registerUser, loginUser ,getUserInfo} = require('./controller/UserController');
const {addReservation,getReservations,updateReservationStatus} = require('./controller/ReservationController');
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, 'public/images');
  },
  filename: (req, file, cb) => {
    const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
    cb(null, uniqueSuffix + '-' + file.originalname);
  }
});
const upload = multer({ storage: storage }).any();

app.post('/register', registerUser);
app.post('/login', loginUser);
app.get('/infouser/:id_user', getUserInfo);
app.use(express.json());
app.use('/images', express.static(path.join(__dirname, 'public/images')));
app.get('/hotels', getHotels);
app.post('/hotels', upload, addHotel);
app.put('/hotels/:id_hotel', editHotel);  
app.delete('/hotels/:id_hotel', deleteHotel); 
app.post('/addReservation', addReservation); 
app.get('/reservations/:id_user', getReservations);
app.put('/editreserv/:id_reservation', updateReservationStatus); 
http.createServer(app).listen(port, '0.0.0.0', () => {
  console.log(`Serveur HTTP démarré sur le port ${port}`);
});
