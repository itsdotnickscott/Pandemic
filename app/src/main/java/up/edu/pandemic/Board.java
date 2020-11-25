package up.edu.pandemic;

public class Board {
    // instance variables
    public static final int NUM_CITIES = 48;

    private City[] allCities;

    public Board() {
        // blue cities
        City chicago = new City("Chicago", Disease.BLUE, new float[][]{{333.8261f, 185.81516f}, {473.8261f, 325.81516f}});
        City sanFrancisco = new City("San Francisco", Disease.BLUE, new float[][]{{221.88443f, 233.7752f}, {361.88443f, 373.7752f}});
        City montreal = new City("Montreal", Disease.BLUE, new float[][]{{451.7647f, 177.82182f}, {591.7647f, 317.82184f}});
        City newYork = new City("New York", Disease.BLUE, new float[][]{{554.71106f, 215.79018f}, {694.71106f, 355.79016f}});
        City washington = new City("Washington", Disease.BLUE, new float[][]{{501.73868f, 290.72772f}, {641.73865f, 430.72772f}});
        City atlanta = new City("Atlanta", Disease.BLUE, new float[][]{{386.79852f, 268.74606f}, {526.7985f, 408.74606f}});
        City london = new City("London", Disease.BLUE, new float[][]{{781.5929f, 135.8568f}, {921.5929f, 275.8568f}});
        City essen = new City("Essen", Disease.BLUE, new float[][]{{894.53406f, 111.87677f}, {1034.534f, 251.87677f}});
        City stPetersburg = new City("St. Petersburg", Disease.BLUE, new float[][]{{1018.46954f, 107.8801f}, {1158.4695f, 247.8801f}});
        City madrid = new City("Madrid", Disease.BLUE, new float[][]{{773.59705f, 253.75854f}, {913.59705f, 393.75854f}});
        City paris = new City("Paris", Disease.BLUE, new float[][]{{872.54553f, 208.796f}, {1012.54553f, 348.79602f}});
        City milan = new City("Milan", Disease.BLUE, new float[][]{{959.50024f, 194.80766f}, {1099.5002f, 334.80768f}});

        // yellow cities
        City losAngeles = new City("Los Angeles", Disease.YELLOW, new float[][]{{237.8761f, 338.68777f}, {377.8761f, 478.68777f}});
        City miami = new City("Miami", Disease.YELLOW, new float[][]{{484.74753f, 365.66528f}, {624.74756f, 505.66528f}});
        City mexicoCity = new City("Mexico City", Disease.YELLOW, new float[][]{{351.81674f, 389.6453f}, {491.81674f, 529.64526f}});
        City bogota = new City("Bogota", Disease.YELLOW, new float[][]{{497.74075f, 488.56287f}, {637.7407f, 628.56287f}});
        City lima = new City("Lima", Disease.YELLOW, new float[][]{{406.78812f, 612.45966f}, {546.7881f, 752.45966f}});
        City santiago = new City("Santiago", Disease.YELLOW, new float[][]{{451.7647f, 774.32477f}, {591.7647f, 914.32477f}});
        City buenosAires = new City("Buenos Aires", Disease.YELLOW, new float[][]{{577.6991f, 719.37054f}, {717.6991f, 859.37054f}});
        City saoPaulo = new City("Sao Paulo", Disease.YELLOW, new float[][]{{640.6663f, 624.44965f}, {780.6663f, 764.44965f}});
        City lagos = new City("Lagos", Disease.YELLOW, new float[][]{{838.56323f, 452.59286f}, {978.56323f, 592.5929f}});
        City khartoum = new City("Khartoum", Disease.YELLOW, new float[][]{{1004.4768f, 436.60617f}, {1144.4768f, 576.6062f}});
        City kinshasa = new City("Kinshasa", Disease.YELLOW, new float[][]{{935.51276f, 567.4971f}, {1075.5127f, 707.4971f}});
        City johannesburg = new City("Johannesburg", Disease.YELLOW, new float[][]{{988.48517f, 674.408f}, {1128.4851f, 814.408f}});

        // black cities
        City moscow = new City("Moscow", Disease.BLACK, new float[][]{{1102.4258f, 152.84264f}, {1242.4258f, 292.84265f}});
        City istanbul = new City("Istanbul", Disease.BLACK, new float[][]{{1043.4565f, 239.7702f}, {1183.4565f, 379.7702f}});
        City algiers = new City("Algiers", Disease.BLACK, new float[][]{{909.52625f, 336.68942f}, {1049.5262f, 476.68942f}});
        City tehran = new City("Tehran", Disease.BLACK, new float[][]{{1175.3878f, 217.78851f}, {1315.3878f, 357.7885f}});
        City baghdad = new City("Baghdad", Disease.BLACK, new float[][]{{1117.418f, 292.72607f}, {1257.418f, 432.72607f}});
        City cairo = new City("Cairo", Disease.BLACK, new float[][]{{1006.47577f, 356.6728f}, {1146.4758f, 496.6728f}});
        City riyadh = new City("Riyadh", Disease.BLACK, new float[][]{{1094.4299f, 391.64365f}, {1234.4299f, 531.6437f}});
        City karachi = new City("Karachi", Disease.BLACK, new float[][]{{1208.3706f, 333.69193f}, {1348.3706f, 473.69193f}});
        City delhi = new City("Delhi", Disease.BLACK, new float[][]{{1278.3342f, 248.7627f}, {1418.3342f, 388.7627f}});
        City mumbai = new City("Mumbai", Disease.BLACK, new float[][]{{1212.3685f, 451.5937f}, {1352.3685f, 591.5937f}});
        City chennai = new City("Chennai", Disease.BLACK, new float[][]{{1304.3207f, 480.56952f}, {1444.3207f, 620.5695f}});
        City kolkata = new City("Kolkata", Disease.BLACK, new float[][]{{1335.3044f, 340.6861f}, {1475.3044f, 480.6861f}});

        // red cities
        City beijing = new City("Beijing", Disease.RED, new float[][]{{1380.2811f, 134.85762f}, {1520.2811f, 274.8576f}});
        City seoul = new City("Seoul", Disease.RED, new float[][]{{1495.2212f, 127.86345f}, {1635.2212f, 267.86346f}});
        City tokyo = new City("Tokyo", Disease.RED, new float[][]{{1585.1743f, 191.81017f}, {1725.1743f, 331.81018f}});
        City shanghai = new City("Shanghai", Disease.RED, new float[][]{{1400.2706f, 226.78104f}, {1540.2706f, 366.78104f}});
        City hongKong = new City("Hong Kong", Disease.RED, new float[][]{{1429.2556f, 313.7086f}, {1569.2556f, 453.7086f}});
        City taipei = new City("Taipei", Disease.RED, new float[][]{{1567.1837f, 371.66028f}, {1707.1837f, 511.66028f}});
        City osaka = new City("Osaka", Disease.RED, new float[][]{{1613.1598f, 282.7344f}, {1753.1598f, 422.7344f}});
        City bangkok = new City("Bangkok", Disease.RED, new float[][]{{1378.2821f, 424.61615f}, {1518.2821f, 564.61615f}});
        City hoChiMinhCity = new City("Ho Chi Minh City", Disease.RED, new float[][]{{1465.2368f, 472.5762f}, {1605.2368f, 612.5762f}});
        City manila = new City("Manila", Disease.RED, new float[][]{{1564.1853f, 502.5512f}, {1704.1853f, 642.5512f}});
        City jakarta = new City("Jakarta", Disease.RED, new float[][]{{1385.2784f, 552.5096f}, {1525.2784f, 692.5096f}});
        City sydney = new City("Sydney", Disease.RED, new float[][]{{1617.1577f, 723.3672f}, {1757.1577f, 863.3672f}});

        ///blue connections
        chicago.setConnections(new City[]{sanFrancisco, losAngeles, mexicoCity, atlanta, montreal});
        washington.setConnections(new City[]{atlanta, montreal, newYork, miami});
        atlanta.setConnections(new City[]{washington, chicago, miami});
        newYork.setConnections(new City[]{montreal, washington, madrid, london});
        montreal.setConnections(new City[]{chicago, newYork, washington});
        sanFrancisco.setConnections(new City[]{losAngeles, chicago, osaka, manila});
        london.setConnections(new City[]{essen, newYork, madrid, paris});
        madrid.setConnections(new City[]{newYork, london, paris, saoPaulo, algiers});
        paris.setConnections(new City[]{london, essen, milan, algiers, madrid});
        essen.setConnections(new City[]{london, paris, milan, stPetersburg});
        milan.setConnections(new City[]{essen, paris, istanbul});
        stPetersburg.setConnections(new City[]{essen, istanbul, moscow});

        //yellow connections
        losAngeles.setConnections(new City[]{sydney, sanFrancisco, chicago, mexicoCity});
        mexicoCity.setConnections(new City[]{losAngeles, chicago, miami, bogota, lima});
        miami.setConnections(new City[]{mexicoCity, atlanta, washington, bogota});
        bogota.setConnections(new City[]{mexicoCity, miami, lima, buenosAires, saoPaulo});
        lima.setConnections(new City[]{mexicoCity, bogota, santiago});
        santiago.setConnections(new City[]{lima});
        buenosAires.setConnections(new City[]{bogota, saoPaulo});
        saoPaulo.setConnections(new City[]{buenosAires, bogota, madrid, lagos});
        lagos.setConnections(new City[]{saoPaulo, khartoum, kinshasa});
        khartoum.setConnections(new City[]{cairo, lagos, kinshasa, johannesburg});
        kinshasa.setConnections(new City[]{lagos, khartoum, johannesburg});
        johannesburg.setConnections(new City[]{kinshasa, khartoum});

        //black connections
        istanbul.setConnections(new City[]{stPetersburg, moscow, milan, algiers, cairo, baghdad});
        moscow.setConnections(new City[]{stPetersburg, istanbul, tehran});
        algiers.setConnections(new City[]{madrid, paris, istanbul, cairo});
        cairo.setConnections(new City[]{algiers, istanbul, baghdad, riyadh});
        baghdad.setConnections(new City[]{istanbul, tehran, karachi, riyadh, cairo});
        riyadh.setConnections(new City[]{cairo, baghdad, karachi});
        karachi.setConnections(new City[]{riyadh, baghdad, tehran, delhi, mumbai});
        tehran.setConnections(new City[]{moscow, baghdad, karachi, delhi});
        delhi.setConnections(new City[]{tehran, karachi, mumbai, chennai, kolkata});
        mumbai.setConnections(new City[]{karachi, delhi, chennai});
        kolkata.setConnections(new City[]{delhi, chennai, bangkok, hongKong});
        chennai.setConnections(new City[]{mumbai, delhi, kolkata, bangkok, jakarta});

        //red connections
        bangkok.setConnections(new City[]{kolkata, chennai, hongKong, jakarta, hoChiMinhCity});
        hongKong.setConnections(new City[]{kolkata, bangkok, hoChiMinhCity, manila, taipei, shanghai});
        jakarta.setConnections(new City[]{chennai, bangkok, hoChiMinhCity, sydney});
        hoChiMinhCity.setConnections(new City[]{jakarta, bangkok, hongKong, manila});
        manila.setConnections(new City[]{hoChiMinhCity, hongKong, taipei, sydney, sanFrancisco});
        taipei.setConnections(new City[]{hongKong, osaka, manila, shanghai});
        shanghai.setConnections(new City[]{beijing, seoul, tokyo, hongKong, taipei});
        beijing.setConnections(new City[]{shanghai, seoul});
        seoul.setConnections(new City[]{beijing, shanghai, tokyo});
        tokyo.setConnections(new City[]{seoul, shanghai, osaka});
        osaka.setConnections(new City[]{tokyo, taipei, sanFrancisco});
        sydney.setConnections(new City[]{jakarta, manila, losAngeles});

        this.allCities = new City[]{chicago, sanFrancisco, montreal, newYork, washington, atlanta, london,
                essen, stPetersburg, madrid, paris, milan, losAngeles, miami, mexicoCity, bogota,
                lima, santiago, buenosAires, saoPaulo, lagos, khartoum, kinshasa, johannesburg,
                moscow, istanbul, algiers, tehran, baghdad, cairo, riyadh, karachi, delhi, mumbai,
                chennai, kolkata, beijing, seoul, tokyo, shanghai, hongKong, taipei, osaka, bangkok,
                hoChiMinhCity, manila, jakarta, sydney};
    }

    public Board(Board orig) {
        this.allCities = new City[NUM_CITIES];
        for(int i = 0; i < NUM_CITIES; i++) {
            this.allCities[i] = new City(orig.allCities[i]);
        }
    }

    public City[] getAllCities() {
        return this.allCities;
    }

    /** getCity()
     * This is a helper method which finds a city in the deck.
     * @param city The name of the city to find.
     * @return The city.
     */
    public City getCity(String city) {
        // iterate through the deck until we find the city name
        for(int i = 0; i < NUM_CITIES; i++) {
            if(this.allCities[i].getName().equals(city)) {
                return this.allCities[i];
            }
        }
        return null;
    } // getCity()

    public void resetHasOutbroke() {
        for(int i = 0; i < NUM_CITIES; i++) {
            this.allCities[i].resetHasOutbroke();
        }
    }
}
