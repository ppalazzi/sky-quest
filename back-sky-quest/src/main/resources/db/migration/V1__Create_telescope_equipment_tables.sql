-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS skyquest;

-- Create sequences for all entities
CREATE SEQUENCE IF NOT EXISTS telescope_seq START 1;
CREATE SEQUENCE IF NOT EXISTS camera_seq START 1;
CREATE SEQUENCE IF NOT EXISTS eyepiece_seq START 1;
CREATE SEQUENCE IF NOT EXISTS filter_seq START 1;

-- Create telescope table
CREATE TABLE skyquest.telescope (
    id BIGINT PRIMARY KEY DEFAULT nextval('telescope_seq'),
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    aperture VARCHAR(100) NOT NULL,
    focal_length VARCHAR(100) NOT NULL,
    focal_ratio VARCHAR(50) NOT NULL,
    mount_type VARCHAR(255),
    weight VARCHAR(100),
    price VARCHAR(100),
    UNIQUE(brand, model)
);

-- Create camera table
CREATE TABLE skyquest.camera (
    id BIGINT PRIMARY KEY DEFAULT nextval('camera_seq'),
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    sensor_type VARCHAR(100),
    resolution VARCHAR(100),
    pixel_size VARCHAR(100),
    cooling_type VARCHAR(100),
    interface_type VARCHAR(100),
    weight VARCHAR(100),
    price VARCHAR(100),
    description TEXT,
    UNIQUE(brand, model)
);

-- Create eyepiece table
CREATE TABLE skyquest.eyepiece (
    id BIGINT PRIMARY KEY DEFAULT nextval('eyepiece_seq'),
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    focal_length VARCHAR(100) NOT NULL,
    apparent_fov VARCHAR(100),
    barrel_size VARCHAR(100),
    eye_relief VARCHAR(100),
    field_stop VARCHAR(100),
    weight VARCHAR(100),
    price VARCHAR(100),
    description TEXT,
    UNIQUE(brand, model)
);

-- Create filter table
CREATE TABLE skyquest.filter (
    id BIGINT PRIMARY KEY DEFAULT nextval('filter_seq'),
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    filter_type VARCHAR(255) NOT NULL,
    thread_size VARCHAR(100),
    wavelength VARCHAR(100),
    transmission VARCHAR(100),
    optical_density VARCHAR(100),
    application VARCHAR(255),
    weight VARCHAR(100),
    price VARCHAR(100),
    description TEXT,
    UNIQUE(brand, model)
);

-- Insert sample telescope data from TypeScript examples
INSERT INTO skyquest.telescope (brand, model, type, aperture, focal_length, focal_ratio, mount_type, weight, price) VALUES
-- Celestron telescopes
('Celestron', 'NexStar 8SE', 'SCHMIDT_CASSEGRAIN', '203mm (8 in)', '2032mm', 'f/10', 'Alt-Az GoTo', '15 kg', '$1,299'),
('Celestron', 'NexStar 6SE', 'SCHMIDT_CASSEGRAIN', '152mm (6 in)', '1500mm', 'f/10', 'Alt-Az GoTo', '13.6 kg', '$999'),
('Celestron', 'AstroMaster 130EQ', 'REFLECTOR', '130mm (5.1 in)', '650mm', 'f/5', 'Equatorial', '8.6 kg', '$349'),
('Celestron', 'EdgeHD 11', 'SCHMIDT_CASSEGRAIN', '279mm (11 in)', '2800mm', 'f/10', 'CGE Pro', '27 kg', '$4,599'),

-- Orion telescopes
('Orion', 'SkyQuest XT8', 'DOBSONIAN', '203mm (8 in)', '1200mm', 'f/5.9', 'Dobsonian', '20 kg', '$449'),
('Orion', 'SkyQuest XT10', 'DOBSONIAN', '254mm (10 in)', '1200mm', 'f/4.7', 'Dobsonian', '24 kg', '$599'),
('Orion', 'StarBlast 6', 'REFLECTOR', '152mm (6 in)', '750mm', 'f/5', 'Table-top Dobsonian', '10 kg', '$329'),

-- Sky-Watcher telescopes
('Sky-Watcher', 'Flextube 300P SynScan', 'DOBSONIAN', '305mm (12 in)', '1500mm', 'f/4.9', 'GoTo Dobsonian', '28 kg', '$1,899'),
('Sky-Watcher', 'Explorer 150PDS', 'REFLECTOR', '150mm (6 in)', '750mm', 'f/5', 'Equatorial', '6.5 kg', '$499'),
('Sky-Watcher', 'Evostar 120ED', 'REFRACTOR', '120mm (4.7 in)', '900mm', 'f/7.5', NULL, '6 kg', '$1,299'),

-- Meade telescopes
('Meade', 'LX90 8-inch', 'SCHMIDT_CASSEGRAIN', '203mm (8 in)', '2000mm', 'f/10', 'Alt-Az GoTo', '17 kg', '$1,599'),
('Meade', 'LightBridge 10-inch', 'DOBSONIAN', '254mm (10 in)', '1270mm', 'f/5', 'Dobsonian', '23 kg', '$799'),

-- Explore Scientific telescopes
('Explore Scientific', 'ED127 Essential', 'REFRACTOR', '127mm (5 in)', '952mm', 'f/7.5', NULL, '6.8 kg', '$1,499'),

-- William Optics telescopes
('William Optics', 'RedCat 51', 'REFRACTOR', '51mm (2 in)', '250mm', 'f/4.9', NULL, '0.8 kg', '$649'),
('William Optics', 'ZenithStar 73', 'REFRACTOR', '73mm (2.9 in)', '430mm', 'f/5.9', NULL, '2.1 kg', '$749'),

-- Takahashi telescopes
('Takahashi', 'FSQ-106EDX4', 'REFRACTOR', '106mm (4.2 in)', '530mm', 'f/5', NULL, '5.5 kg', '$7,995');

-- Insert sample camera data
INSERT INTO skyquest.camera (brand, model, sensor_type, resolution, pixel_size, cooling_type, interface_type, weight, price, description) VALUES
('ZWO', 'ASI533MC Pro', 'CMOS', '3008x3008', '3.76μm', 'TEC Cooled', 'USB 3.0', '665g', '$899', 'Color astronomy camera with Sony IMX533 sensor'),
('ZWO', 'ASI294MM Pro', 'CMOS', '4144x2822', '4.63μm', 'TEC Cooled', 'USB 3.0', '720g', '$1,199', 'Monochrome astronomy camera with Sony IMX294 sensor'),
('QHY', 'QHY268M', 'CMOS', '6280x4210', '3.76μm', 'TEC Cooled', 'USB 3.0', '1200g', '$2,299', 'Monochrome camera with Sony IMX571 APS-C sensor'),
('SBIG', 'STF-8300M', 'CCD', '3326x2504', '5.4μm', 'TEC Cooled', 'USB 2.0', '800g', '$3,995', 'Monochrome CCD camera for deep sky imaging'),
('Atik', '460EX', 'CCD', '2750x2200', '4.54μm', 'TEC Cooled', 'USB 2.0', '850g', '$2,799', 'Color CCD camera with excellent sensitivity');

-- Insert sample eyepiece data
INSERT INTO skyquest.eyepiece (brand, model, focal_length, apparent_fov, barrel_size, eye_relief, field_stop, weight, price, description) VALUES
('Tele Vue', 'Nagler 31mm', '31mm', '82°', '2"', '19mm', '42mm', '680g', '$595', 'Ultra-wide field premium eyepiece'),
('Tele Vue', 'Ethos 21mm', '21mm', '100°', '2"', '15mm', '36mm', '815g', '$895', 'Extreme wide field eyepiece'),
('Explore Scientific', '24mm 68° Ar', '24mm', '68°', '2"', '16mm', '28.5mm', '520g', '$299', 'Wide field eyepiece with argon gas'),
('Baader', 'Morpheus 17.5mm', '17.5mm', '76°', '2"', '17mm', '23mm', '360g', '$359', 'Wide field eyepiece with excellent eye relief'),
('Celestron', 'X-Cel LX 25mm', '25mm', '55°', '1.25"', '20mm', '24mm', '180g', '$89', 'Good value eyepiece with long eye relief');

-- Insert sample filter data
INSERT INTO skyquest.filter (brand, model, filter_type, thread_size, wavelength, transmission, optical_density, application, weight, price, description) VALUES
('Baader', 'UHC-S', 'Narrowband', '2"', '486nm, 656nm', '90%', NULL, 'Deep Sky', '45g', '$199', 'Ultra High Contrast filter for nebulae'),
('Astronomik', 'OIII 12nm', 'Narrowband', '2"', '500.7nm', '12nm FWHM', NULL, 'Deep Sky', '40g', '$289', 'Oxygen III narrowband filter'),
('Chroma', 'H-alpha 3nm', 'Narrowband', '2"', '656.3nm', '3nm FWHM', NULL, 'Deep Sky', '50g', '$449', 'Hydrogen-alpha ultra narrowband filter'),
('ZWO', 'L-Pro', 'Light Pollution', '2"', '400-700nm', '90%', NULL, 'Deep Sky', '35g', '$129', 'Light pollution suppression filter'),
('Optolong', 'UV/IR Cut', 'Broadband', '2"', '400-700nm', '95%', NULL, 'Deep Sky', '30g', '$79', 'UV and IR blocking filter for color cameras'),
('Baader', 'Solar Continuum', 'Solar', '2"', '540nm', '1%', 'ND 2.0', 'Solar', '40g', '$159', 'Solar observation filter for white light'),
('Celestron', 'Moon & Skyglow', 'Light Pollution', '1.25"', '400-700nm', '85%', NULL, 'Lunar/Planetary', '25g', '$49', 'Light pollution and moon brightness filter');

-- Create indexes for better performance
CREATE INDEX idx_telescope_brand ON skyquest.telescope(brand);
CREATE INDEX idx_telescope_type ON skyquest.telescope(type);
CREATE INDEX idx_eyepiece_brand ON skyquest.eyepiece(brand);
CREATE INDEX idx_eyepiece_focal_length ON skyquest.eyepiece(focal_length);
CREATE INDEX idx_filter_brand ON skyquest.filter(brand);
CREATE INDEX idx_filter_type ON skyquest.filter(filter_type);
CREATE INDEX idx_filter_application ON skyquest.filter(application);