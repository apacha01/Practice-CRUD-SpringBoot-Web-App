-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-03-2023 a las 21:05:06
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
  `name` enum('AFRICA','AMERICA','ANTARTIDA','ASIA','EUROPA','OCEANIA') COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `continents`
--

INSERT INTO `continents` (`id_continent`, `name`) VALUES
(1, 'AFRICA'),
(2, 'AMERICA'),
(3, 'ANTARTIDA'),
(4, 'ASIA'),
(5, 'EUROPA'),
(6, 'OCEANIA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employees`
--

CREATE TABLE `employees` (
  `id_employee` int(11) NOT NULL,
  `type` enum('ADMIN','KEEPER','GUIDE') COLLATE utf8_spanish2_ci NOT NULL,
  `user_name` varchar(16) COLLATE utf8_spanish2_ci NOT NULL,
  `password` varchar(16) COLLATE utf8_spanish2_ci NOT NULL,
  `name` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `address` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `first_day` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `employees`
--

INSERT INTO `employees` (`id_employee`, `type`, `user_name`, `password`, `name`, `address`, `phone`, `first_day`) VALUES
(19, 'GUIDE', 'grillo', '123', 'Pepito Grillo', 'un arbol', '12345678', '1940-02-22'),
(23, 'GUIDE', 'mago', '123', 'Merlin', 'algun castillo', '12345678', '1963-03-19'),
(24, 'ADMIN', 'Micky', 'root', 'Micky Mouse', 'Castillo de Disney', '12345678', '1928-11-18'),
(25, 'KEEPER', 'pato', '123', 'Donald Duck', 'supongo q con mickey', '12345678', '1934-05-02'),
(26, 'KEEPER', 'perro', '123', 'Goofy', 'supongo q con mickey', '12345678', '1932-03-27'),
(43, 'ADMIN', 'admin', 'admin', 'Agustín Pacheco', 'abajo de un puente', '1512345678', '2001-09-19'),
(44, 'KEEPER', 'chanchito feliz', '123', 'Peter Pig', 'una choza', '1512345678', '1934-05-02');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `guides`
--

CREATE TABLE `guides` (
  `id` int(11) NOT NULL,
  `id_employee` int(11) NOT NULL,
  `id_itinerary` int(11) NOT NULL,
  `assigned_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `guides`
--

INSERT INTO `guides` (`id`, `id_employee`, `id_itinerary`, `assigned_date`) VALUES
(48, 23, 12, '2023-02-16 20:06:10'),
(49, 23, 14, '2023-02-16 20:06:11'),
(53, 19, 15, '2023-02-21 18:33:50'),
(55, 19, 16, '2023-03-02 13:12:03'),
(57, 23, 19, '2023-03-03 16:19:05'),
(58, 19, 18, '2023-03-03 16:23:23');

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

--
-- Volcado de datos para la tabla `habitats`
--

INSERT INTO `habitats` (`id_habitat`, `name`, `weather`, `vegetation`) VALUES
(1, 'pastizales', 'soleado', 'hierbas altas'),
(2, 'sabana', 'templado', 'hierbas bajas'),
(3, 'bosque', 'humedo', 'arboles'),
(5, 'llanura', 'soleado', 'arbustos'),
(6, 'desierto', 'seco', 'cactus');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitats_continents`
--

CREATE TABLE `habitats_continents` (
  `id_habitat` int(11) NOT NULL,
  `id_continent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `habitats_continents`
--

INSERT INTO `habitats_continents` (`id_habitat`, `id_continent`) VALUES
(1, 1),
(2, 1),
(5, 2),
(3, 1),
(3, 2),
(3, 4),
(3, 5),
(6, 1),
(5, 5),
(1, 6),
(2, 2),
(2, 5);

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

--
-- Volcado de datos para la tabla `itineraries`
--

INSERT INTO `itineraries` (`id_itinerary`, `code`, `duration`, `route_length`, `max_people`, `num_species_visited`, `assigned`) VALUES
(12, 'it147', '15:35:10', 320, 35, 7, 1),
(14, 'it123', '15:35:10', 400, 20, 6, 1),
(15, 'it456', '15:35:10', 50, 5, 1, 1),
(16, 'it789', '15:35:10', 255, 15, 4, 1),
(17, 'it1234', '01:25:10', 125.6, 12, 5, 0),
(18, 'it12345', '01:45:10', 155.17, 15, 6, 1),
(19, 'it1001', '01:15:37', 67.4, 9, 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `itineraries_route`
--

CREATE TABLE `itineraries_route` (
  `id_zone` int(11) NOT NULL,
  `id_itinerary` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `itineraries_route`
--

INSERT INTO `itineraries_route` (`id_zone`, `id_itinerary`) VALUES
(6, 14),
(6, 15),
(16, 12),
(17, 12),
(6, 12),
(16, 16),
(9, 16),
(9, 18),
(17, 19),
(9, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `species`
--

CREATE TABLE `species` (
  `id_species` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `scientific_name` varchar(60) COLLATE utf8_spanish2_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `id_zone` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `species`
--

INSERT INTO `species` (`id_species`, `name`, `scientific_name`, `description`, `id_zone`) VALUES
(1, 'tigre', 'tigriris', 'es un tigre.', NULL),
(2, 'leon', 'leonidas', 'es un leon.', 6),
(3, 'mono', 'moninus', 'es un mono.', NULL),
(5, 'pajaro', 'pajaro pajarus', 'es una pajaro.', 9),
(6, 'pantera', 'panteritus', 'es una pantera.', 6),
(7, 'pantera rosa', 'panteritus rosaditus', 'es una pantera de color rosa.', 6),
(10, 'cerdo', 'cerditorus', 'es un cerdo.', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `species_habitats`
--

CREATE TABLE `species_habitats` (
  `id_species` int(11) NOT NULL,
  `id_habitat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `species_habitats`
--

INSERT INTO `species_habitats` (`id_species`, `id_habitat`) VALUES
(2, 2),
(2, 3),
(2, 5),
(1, 1),
(1, 3),
(3, 2),
(3, 3),
(5, 2),
(5, 3),
(5, 5),
(6, 1),
(6, 2),
(7, 1),
(7, 2),
(2, 1),
(10, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `species_keepers`
--

CREATE TABLE `species_keepers` (
  `id` int(11) NOT NULL,
  `id_employee` int(11) NOT NULL,
  `id_species` int(11) NOT NULL,
  `assigned_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `species_keepers`
--

INSERT INTO `species_keepers` (`id`, `id_employee`, `id_species`, `assigned_date`) VALUES
(42, 26, 1, '2023-02-16 00:44:25'),
(52, 26, 2, '2023-02-20 15:04:35'),
(58, 26, 6, '2023-02-23 11:41:42'),
(60, 26, 7, '2023-02-23 11:41:57'),
(62, 44, 3, '2023-02-23 11:49:11'),
(63, 44, 5, '2023-02-23 11:49:11'),
(64, 44, 10, '2023-02-23 11:49:11'),
(67, 25, 11, '2023-03-02 11:10:27'),
(68, 26, 11, '2023-03-02 11:10:29'),
(69, 44, 11, '2023-03-02 11:10:29'),
(70, 44, 2, '2023-03-02 13:09:11'),
(71, 25, 3, '2023-03-02 13:27:52'),
(72, 25, 10, '2023-03-02 13:27:52');

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
-- Volcado de datos para la tabla `zones`
--

INSERT INTO `zones` (`id_zone`, `name`, `extension`) VALUES
(6, 'zona1', 137.18),
(9, 'zona4', 100),
(16, 'zona extra', 35),
(17, 'zona6', 126.17);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `continents`
--
ALTER TABLE `continents`
  ADD PRIMARY KEY (`id_continent`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indices de la tabla `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id_employee`),
  ADD UNIQUE KEY `user_name` (`user_name`);

--
-- Indices de la tabla `guides`
--
ALTER TABLE `guides`
  ADD PRIMARY KEY (`id`);

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
-- Indices de la tabla `species_keepers`
--
ALTER TABLE `species_keepers`
  ADD PRIMARY KEY (`id`);

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
  MODIFY `id_continent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `employees`
--
ALTER TABLE `employees`
  MODIFY `id_employee` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `guides`
--
ALTER TABLE `guides`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT de la tabla `habitats`
--
ALTER TABLE `habitats`
  MODIFY `id_habitat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `itineraries`
--
ALTER TABLE `itineraries`
  MODIFY `id_itinerary` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `species`
--
ALTER TABLE `species`
  MODIFY `id_species` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `species_keepers`
--
ALTER TABLE `species_keepers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT de la tabla `zones`
--
ALTER TABLE `zones`
  MODIFY `id_zone` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
