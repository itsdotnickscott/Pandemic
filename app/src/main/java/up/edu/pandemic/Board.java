package up.edu.pandemic;

public class Board {
    // instance variables
    public static final int NUM_CITIES = 48;

    private City[] allCities;

    public Board() {
        // blue cities
        City chicago = new City("Chicago", Disease.BLUE, new float[][]{{175.81f, 243.72f}, {261.8f, 314.7f}});
        City sanFrancisco = new City("San Francisco", Disease.BLUE, new float[][]{{28.89f, 287.68f}, {132.8f, 371.6f}});
        City montreal = new City("Montreal", Disease.BLUE, new float[][]{{289.76f, 239.72f}, {388.7f, 328.7f}});
        City newYork = new City("New York", Disease.BLUE, new float[][]{{378.71f, 255.71f}, {482.7f, 343.6f}});
        City washington = new City("Washington", Disease.BLUE, new float[][]{{342.73f, 334.64f}, {452.67f, 421.6f}});
        City atlanta = new City("Atlanta", Disease.BLUE, new float[][]{{216.79f, 331.64f}, {299.8f, 420.6f}});
        City london = new City("London", Disease.BLUE, new float[][]{{596.6f, 187.76f}, {674.6f, 260.7f}});
        City essen = new City("Essen", Disease.BLUE, new float[][]{{709.54f, 169.78f}, {785.5f, 243.7f}});
        City stPetersburg = new City("St. Petersburg", Disease.BLUE, new float[][]{{834.47f, 145.80f}, {922.4f, 217.7f}});
        City madrid = new City("Madrid", Disease.BLUE, new float[][]{{576.61f, 302.67f}, {663.6f, 388.6f}});
        City paris = new City("Paris", Disease.BLUE, new float[][]{{677.56f, 243.72f}, {770.5f, 339.6f}});
        City milan = new City("Milan", Disease.BLUE, new float[][]{{755.51f, 220.74f}, {854.5f, 310.7f}});

        // yellow cities
        City losAngeles = new City("Los Angeles", Disease.YELLOW, new float[][]{{51.88f, 401.59f}, {151.8f, 490.5f}});
        City miami = new City("Miami", Disease.YELLOW, new float[][]{{294.75f, 431.56f}, {395.7f, 520.5f}});
        City mexicoCity = new City("Mexico City", Disease.YELLOW, new float[][]{{162.82f, 449.55f}, {252.8f, 526.5f}});
        City bogota = new City("Bogota", Disease.YELLOW, new float[][]{{276.76f, 546.46f}, {371.7f, 631.4f}});
        City lima = new City("Lima", Disease.YELLOW, new float[][]{{231.79f, 659.37f}, {342.7f, 754.3f}});
        City santiago = new City("Santiago", Disease.YELLOW, new float[][]{{255.77f, 797.26f}, {363.7f, 887.2f}});
        City buenosAires = new City("Buenos Aires", Disease.YELLOW, new float[][]{{376.71f, 771.28f}, {490.7f, 869.2f}});
        City saoPaulo = new City("Sao Paulo", Disease.YELLOW, new float[][]{{446.67f, 688.35f}, {553.6f, 778.3f}});
        City lagos = new City("Lagos", Disease.YELLOW, new float[][]{{664.56f, 524.48f}, {769.5f, 615.5f}});
        City khartoum = new City("Khartoum", Disease.YELLOW, new float[][]{{664.56f, 524.48f}, {922.4f, 595.4f}});
        City kinshasa = new City("Kinshasa", Disease.YELLOW, new float[][]{{740.52f, 606.41f}, {842.5f, 691.3f}});
        City johannesburg = new City("Johannesburg", Disease.YELLOW, new float[][]{{801.49f, 715.32f}, {911.4f, 806.3f}});

        // black cities
        City moscow = new City("Moscow", Disease.BLACK, new float[][]{{897.44f, 218.74f}, {1000.4f, 305.7f}});
        City istanbul = new City("Istanbul", Disease.BLACK, new float[][]{{801.49f, 287.68f}, {915.4f, 376.6f}});
        City algiers = new City("Algiers", Disease.BLACK, new float[][]{{702.54f, 362.62f}, {806.5f, 448.6f}});
        City tehran = new City("Tehran", Disease.BLACK, new float[][]{{979.4f, 265.7f}, {1095.3f, 345.6f}});
        City baghdad = new City("Baghdad", Disease.BLACK, new float[][]{{883.45f, 351.63f}, {994.4f, 426.6f}});
        City cairo = new City("Cairo", Disease.BLACK, new float[][]{{790.49f, 386.60f}, {905.4f, 471.5f}});
        City riyadh = new City("Riyadh", Disease.BLACK, new float[][]{{900.44f, 451.54f}, {1017.4f, 549.5f}});
        City karachi = new City("Karachi", Disease.BLACK, new float[][]{{1001.38f, 389.6f}, {1112.3f, 469.5f}});
        City delhi = new City("Delhi", Disease.BLACK, new float[][]{{1091.34f, 348.63f}, {1202.3f, 428.6f}});
        City mumbai = new City("Mumbai", Disease.BLACK, new float[][]{{1004.38f, 478.52f}, {1117.3f, 559.5f}});
        City chennai = new City("Chennai", Disease.BLACK, new float[][]{{1089.34f, 533.48f}, {1203.2f, 621.4f}});
        City kolkata = new City("Kolkata", Disease.BLACK, new float[][]{{1167.32f, 387.6f}, {1280.2f, 464.5f}});

        // red cities
        City beijing = new City("Beijing", Disease.RED, new float[][]{{1229.27f, 251.71f}, {1342.2f, 329.7f}});
        City seoul = new City("Seoul", Disease.RED, new float[][]{{1331.21f, 241.72f}, {1444.2f, 329.7f}});
        City tokyo = new City("Tokyo", Disease.RED, new float[][]{{1414.17f, 293.68f}, {1520.1f, 374.6f}});
        City shanghai = new City("Shanghai", Disease.RED, new float[][]{{1244.26f, 336.64f}, {1357.2f, 421.6f}});
        City hongKong = new City("Hong Kong", Disease.RED, new float[][]{{1243.26f, 429.56f}, {1349.2f, 516.5f}});
        City taipei = new City("Taipei", Disease.RED, new float[][]{{1346.21f, 411.58f}, {1464.1f, 500.5f}});
        City osaka = new City("Osaka", Disease.RED, new float[][]{{1421.17f, 377.61f}, {1533.1f, 478.5f}});
        City bangkok = new City("Bangkok", Disease.RED, new float[][]{{1178.29f, 493.51f}, {1288.2f, 574.4f}});
        City hoChiMinhCity = new City("Ho Chi Minh City", Disease.RED, new float[][]{{1252.25f, 558.45f}, {1368.1f, 644.4f}});
        City manila = new City("Manila", Disease.RED, new float[][]{{1368.19f, 558.45f}, {1476.1f, 652.4f}});
        City jakarta = new City("Jakarta", Disease.RED, new float[][]{{1173.30f, 626.40f}, {1285.2f, 719.3f}});
        City sydney = new City("Sydney", Disease.RED, new float[][]{{1433.16f, 793.26f}, {1554.1f, 887.2f}});

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
