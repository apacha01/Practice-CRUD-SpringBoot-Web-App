-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-11-2022 a las 18:36:43
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `zoo`
--
CREATE DATABASE IF NOT EXISTS `zoo` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci;
USE `zoo`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `continents`
--

CREATE TABLE `continents` (
  `id_continent` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employees`
--

CREATE TABLE `employees` (
  `id_employee` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  `user_name` varchar(16) COLLATE utf8_spanish2_ci NOT NULL,
  `password` varchar(16) COLLATE utf8_spanish2_ci NOT NULL,
  `name` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `address` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `first_day` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `guides`
--

CREATE TABLE `guides` (
  `id_employee` int(11) NOT NULL,
  `id_itinerary` int(11) NOT NULL,
  `assigned_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitats`
--

CREATE TABLE `habitats` (
  `id_habitat` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `weather` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `vegetation` varchar(20) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitats_continents`
--

CREATE TABLE `habitats_continents` (
  `id_habitat` int(11) NOT NULL,
  `id_continent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `itineraries`
--

CREATE TABLE `itineraries` (
  `id_itinerary` int(11) NOT NULL,
  `code` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `duration` time NOT NULL,
  `route_length` double NOT NULL,
  `max_people` int(11) NOT NULL,
  `num_species_visited` int(11) NOT NULL,
  `assigned` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `itineraries_route`
--

CREATE TABLE `itineraries_route` (
  `id_zone` int(11) NOT NULL,
  `id_itinerary` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `species`
--

CREATE TABLE `species` (
  `id_species` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `scientific_name` varchar(60) COLLATE utf8_spanish2_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `id_zone` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `species_habitats`
--

CREATE TABLE `species_habitats` (
  `id_species` int(11) NOT NULL,
  `id_habitat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `species_keepers`
--

CREATE TABLE `species_keepers` (
  `id_employee` int(11) NOT NULL,
  `id_species` int(11) NOT NULL,
  `assignedDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `types`
--

CREATE TABLE `types` (
  `id_type` int(11) NOT NULL,
  `name` varchar(15) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zones`
--

CREATE TABLE `zones` (
  `id_zone` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `extension` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `continents`
--
ALTER TABLE `continents`
  ADD PRIMARY KEY (`id_continent`);

--
-- Indices de la tabla `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id_employee`);

--
-- Indices de la tabla `habitats`
--
ALTER TABLE `habitats`
  ADD PRIMARY KEY (`id_habitat`);

--
-- Indices de la tabla `itineraries`
--
ALTER TABLE `itineraries`
  ADD PRIMARY KEY (`id_itinerary`);

--
-- Indices de la tabla `species`
--
ALTER TABLE `species`
  ADD PRIMARY KEY (`id_species`);

--
-- Indices de la tabla `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`id_type`);

--
-- Indices de la tabla `zones`
--
ALTER TABLE `zones`
  ADD PRIMARY KEY (`id_zone`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `continents`
--
ALTER TABLE `continents`
  MODIFY `id_continent` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `employees`
--
ALTER TABLE `employees`
  MODIFY `id_employee` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `habitats`
--
ALTER TABLE `habitats`
  MODIFY `id_habitat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `itineraries`
--
ALTER TABLE `itineraries`
  MODIFY `id_itinerary` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `species`
--
ALTER TABLE `species`
  MODIFY `id_species` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `types`
--
ALTER TABLE `types`
  MODIFY `id_type` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `zones`
--
ALTER TABLE `zones`
  MODIFY `id_zone` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
