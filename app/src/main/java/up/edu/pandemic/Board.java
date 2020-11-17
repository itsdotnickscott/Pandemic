package up.edu.pandemic;

public class Board {
    // instance variables
    public static final int NUM_CITIES = 48;

    private City[] allCities;

    public Board() {
        // blue cities
        City chicago = new City("Chicago", Disease.BLUE, new float[][]{{156.82f, 150.79f}, {295.75f, 288.68f}});
        City sanFrancisco = new City("San Francisco", Disease.BLUE, new float[][]{{47.88f, 200.75f}, {187.88f, 340.75f}});
        City montreal = new City("Montreal", Disease.BLUE, new float[][]{{278.76f, 139.80f}, {418.76f, 279.80f}});
        City newYork = new City("New York", Disease.BLUE, new float[][]{{380.71f, 179.77f}, {520.71f, 319.77f}});
        City washington = new City("Washington", Disease.BLUE, new float[][]{{329.739f, 257.71f}, {469.73f, 397.71f}});
        City atlanta = new City("Atlanta", Disease.BLUE, new float[][]{{213.80f, 238.72f}, {353.80f, 378.72f}});
        City london = new City("London", Disease.BLUE, new float[][]{{609.59f, 97.84f}, {749.59f, 237.84f}});
        City essen = new City("Essen", Disease.BLUE, new float[][]{{722.53f, 78.85f}, {862.53f, 218.85f}});
        City stPetersburg = new City("St. Petersburg", Disease.BLUE, new float[][]{{850.46f, 74.86f}, {990.46f, 214.86f}});
        City madrid = new City("Madrid", Disease.BLUE, new float[][]{{596.60f, 222.73f}, {736.60f, 362.73f}});
        City paris = new City("Paris", Disease.BLUE, new float[][]{{699.54f, 175.77f}, {839.54f, 315.77f}});
        City milan = new City("Milan", Disease.BLUE, new float[][]{{791.49f, 163.78f}, {931.49f, 303.78f}});

        // yellow cities
        City losAngeles = new City("Los Angeles", Disease.YELLOW, new float[][]{{64.87f, 304.67f}, {204.87f, 444.67f}});
        City miami = new City("Miami", Disease.YELLOW, new float[][]{{312.74f, 332.64f}, {452.74f, 472.64f}});
        City mexicoCity = new City("Mexico City", Disease.YELLOW, new float[][]{{177.81f, 357.62f}, {317.81f, 497.62f}});
        City bogota = new City("Bogota", Disease.YELLOW, new float[][]{{317.74f, 453.54f}, {457.74f, 593.54f}});
        City lima = new City("Lima", Disease.YELLOW, new float[][]{{235.78f, 576.44f}, {375.78f, 716.44f}});
        City santiago = new City("Santiago", Disease.YELLOW, new float[][]{{282.76f, 741.30f}, {422.76f, 881.30f}});
        City buenosAires = new City("Buenos Aires", Disease.YELLOW, new float[][]{{405.70f, 685.35f}, {545.70f, 825.35f}});
        City saoPaulo = new City("Sao Paulo", Disease.YELLOW, new float[][]{{470.66f, 591.43f}, {610.66f, 731.43f}});
        City lagos = new City("Lagos", Disease.YELLOW, new float[][]{{674.56f, 422.57f}, {814.56f, 562.57f}});
        City khartoum = new City("Khartoum", Disease.YELLOW, new float[][]{{829.47f, 400.59f}, {969.47f, 540.59f}});
        City kinshasa = new City("Kinshasa", Disease.YELLOW, new float[][]{{762.51f, 535.47f}, {902.51f, 675.47f}});
        City johannesburg = new City("Johannesburg", Disease.YELLOW, new float[][]{{813.48f, 642.38f}, {953.48f, 782.38f}});

        // black cities
        City moscow = new City("Moscow", Disease.BLACK, new float[][]{{928.42f, 117.82f}, {1068.42f, 257.82f}});
        City istanbul = new City("Istanbul", Disease.BLACK, new float[][]{{871.45f, 203.75f}, {1011.45f, 343.75f}});
        City algiers = new City("Algiers", Disease.BLACK, new float[][]{{735.52f, 298.67f}, {875.52f, 438.67f}});
        City tehran = new City("Tehran", Disease.BLACK, new float[][]{{1000.39f, 181.77f}, {1140.39f, 321.77f}});
        City baghdad = new City("Baghdad", Disease.BLACK, new float[][]{{944.41f, 266.70f}, {1084.41f, 406.70f}});
        City cairo = new City("Cairo", Disease.BLACK, new float[][]{{833.47f, 319.65f}, {973.47f, 459.65f}});
        City riyadh = new City("Riyadh", Disease.BLACK, new float[][]{{918.43f, 361.62f}, {1058.43f, 501.62f}});
        City karachi = new City("Karachi", Disease.BLACK, new float[][]{{1034.37f, 299.67f}, {1174.37f, 439.67f}});
        City delhi = new City("Delhi", Disease.BLACK, new float[][]{{1098.33f, 210.74f}, {1238.33f, 350.74f}});
        City mumbai = new City("Mumbai", Disease.BLACK, new float[][]{{1034.37f, 416.57f}, {1174.37f, 556.57f}});
        City chennai = new City("Chennai", Disease.BLACK, new float[][]{{1129.32f, 446.55f}, {1269.32f, 586.55f}});
        City kolkata = new City("Kolkata", Disease.BLACK, new float[][]{{1162.30f, 306.66f}, {1302.30f, 446.66f}});

        // red cities
        City beijing = new City("Beijing", Disease.RED, new float[][]{{1201.28f, 100.84f}, {1341.28f, 240.84f}});
        City seoul = new City("Seoul", Disease.RED, new float[][]{{1322.22f, 92.84f}, {1462.22f, 232.84f}});
        City tokyo = new City("Tokyo", Disease.RED, new float[][]{{1413.17f, 163.78f}, {1553.17f, 303.78f}});
        City shanghai = new City("Shanghai", Disease.RED, new float[][]{{1228.27f, 192.76f}, {1368.27f, 332.76f}});
        City hongKong = new City("Hong Kong", Disease.RED, new float[][]{{1256.25f, 280.69f}, {1396.25f, 420.69f}});
        City taipei = new City("Taipei", Disease.RED, new float[][]{{1389.18f, 335.64f}, {1529.18f, 475.64f}});
        City osaka = new City("Osaka", Disease.RED, new float[][]{{1440.16f, 251.71f}, {1580.16f, 391.71f}});
        City bangkok = new City("Bangkok", Disease.RED, new float[][]{{1202.28f, 390.59f}, {1342.28f, 530.59f}});
        City hoChiMinhCity = new City("Ho Chi Minh City", Disease.RED, new float[][]{{1289.24f, 436.56f}, {1429.24f, 576.56f}});
        City manila = new City("Manila", Disease.RED, new float[][]{{1391.18f, 461.54f}, {1531.18f, 601.54f}});
        City jakarta = new City("Jakarta", Disease.RED, new float[][]{{1209.28f, 515.49f}, {1349.28f, 655.49f}});
        City sydney = new City("Sydney", Disease.RED, new float[][]{{1439.16f, 690.34f}, {1579.16f, 830.34f}});

        //blue connections
        chicago.setConnections(new City[]{sanFrancisco, losAngeles, mexicoCity, atlanta, montreal});
        washington.setConnections(new City[]{atlanta, montreal, newYork});
        atlanta.setConnections(new City[]{washington, chicago, miami});
        newYork.setConnections(new City[]{montreal, washington, madrid, london});
        montreal.setConnections(new City[]{chicago, newYork, washington});
        sanFrancisco.setConnections(new City[]{losAngeles, chicago, tokyo, manila});
        london.setConnections(new City[]{essen, newYork, madrid, paris});
        madrid.setConnections(new City[]{newYork, london, paris, saoPaulo, algiers});
        paris.setConnections(new City[]{london, essen, milan, algiers, madrid});
        essen.setConnections(new City[]{london, paris, milan, stPetersburg});
        milan.setConnections(new City[]{essen, paris, istanbul});
        stPetersburg.setConnections(new City[]{essen, istanbul, moscow});

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
        tokyo.setConnections(new City[]{seoul, shanghai, osaka, sanFrancisco});
        osaka.setConnections(new City[]{tokyo, taipei});
        sydney.setConnections(new City[]{jakarta, manila, losAngeles});

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
}
