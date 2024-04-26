// import React from 'react'
// import ReactDOM from 'react-dom/client'
// import App from './App.jsx'
// import './index.css'
// import { BrowserRouter , Routes , Route} from 'react-router-dom'
// import Register from './Component/Public/Register.jsx'
// import Home from './Component/Public/Home.jsx'
// import Login from './Component/Public/Login.jsx'

// ReactDOM.createRoot(document.getElementById('root')).render(
//   <React.StrictMode>
//    <BrowserRouter>
//     <Routes>
//     <Route path='/' element={<App/>}>
//   <Route path='/login' element={<Login/>}/>
//   <Route path=
  
//   '/home' element={<Home/>}/>
//   <Route path='/register' element={<Register/>}/>
//   </Route>
//     </Routes>
//    </BrowserRouter>
//   </React.StrictMode>,
// )
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';
import { BrowserRouter } from 'react-router-dom';
import AllRoutes from './Component/Util/Routes/AllRoutes';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
     <AllRoutes/>
    </BrowserRouter>
  </React.StrictMode>
);
