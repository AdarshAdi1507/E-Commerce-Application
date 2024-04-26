// import './App.css'
// import Header from './Component/Util/Header'
// import {Outlet} from 'react-router-dom'

// function App() {
  

//   return (
// <div>
//   <Header/>
//   <Outlet/>
// </div>
//   )
// }

// export default App

// App.js

import React, { useState } from 'react';

import Header from './Component/Util/Header';
import { Outlet } from 'react-router-dom';

const App = () => {

  return (
    <div>
      <Header/>
      <Outlet/>
    </div>
  );
};
export default App;