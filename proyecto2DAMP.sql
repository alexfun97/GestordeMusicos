-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 23-10-2017 a las 21:40:18
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 7.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto2DAMP`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cantante`
--

CREATE TABLE `cantante` (
  `ID` int(11) NOT NULL,
  `nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `fechaNac` date NOT NULL,
  `nacionalidad` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `genero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cantante`
--

INSERT INTO `cantante` (`ID`, `nombre`, `fechaNac`, `nacionalidad`, `genero`) VALUES
(121, 'Alex', '2017-09-26', 'Español', 1),
(123, 'Jaime', '2017-10-02', 'Inglaterra', 3),
(124, 'Ivan', '2017-10-02', 'Rusia', 4),
(125, 'Alberto', '2017-10-02', 'Danes', 2),
(126, 'Lone', '2017-10-03', 'Italiano', 3),
(130, 'Dani', '2017-09-26', 'Estados Unidos', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

CREATE TABLE `genero` (
  `ID` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `añoCreacion` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`ID`, `nombre`, `añoCreacion`) VALUES
(1, 'Rap', 1968),
(2, 'Trap', 1992),
(3, 'Reggae', 0),
(4, 'Pop', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cantante`
--
ALTER TABLE `cantante`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `genero` (`genero`);

--
-- Indices de la tabla `genero`
--
ALTER TABLE `genero`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cantante`
--
ALTER TABLE `cantante`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=131;
--
-- AUTO_INCREMENT de la tabla `genero`
--
ALTER TABLE `genero`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cantante`
--
ALTER TABLE `cantante`
  ADD CONSTRAINT `cantante_ibfk_1` FOREIGN KEY (`genero`) REFERENCES `genero` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
