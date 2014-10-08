package com.ntii.kawaly.o.jasiu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.chartboost.sdk.*;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JokesList extends Activity implements OnClickListener{
	SQLiteDatabase database;
	ListView lk_lv_jokes_list;
	SimpleAdapter adapter;
	int selected;
	Button lk_b_all,lk_b_new,lk_b_like;
	private boolean doubleBackToExitPressedOnce = false;
	// Reklama
	private Chartboost cb;
	int where=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pe³ny ekran
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Layout
		setContentView(R.layout.jokes_list);
		// Inicjalizacja z xml
		initialize();
		// Baza danych - tworzenie i otwieranie
		database = openOrCreateDatabase("jokes", 0, null);
		if (database.getVersion() == 0) {
			database.execSQL("CREATE TABLE jokes_list(_id INTEGER PRIMARY KEY AUTOINCREMENT,content NVARCHAR,state INTEGER);");
			insertJokes(0);
			database.setVersion(1);
		}
		for (int i=1;i<1;i++)
			if (database.getVersion() == i) {
				insertJokes(i);
				database.setVersion(i+1);
			}
		// Lista, czytanie
		updateList(0);
		// Configure Chartboost
		this.cb = Chartboost.sharedChartboost();
		String appId = "521ba31016ba47fd7e000068";
		String appSignature = "eb87aef8640bfbbd281fb61e421feb3050b1d371";
		this.cb.onCreate(this, appId, appSignature, null);
		// Show an interstitial
		this.cb.showInterstitial();
	}

	private void insertJokes(int revision) {
		switch(revision){
		case(0):
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasio wróci³ ze szko³y i ojciec pyta go jak posz³o."
					+System.getProperty("line.separator")+" - Z matematyki dosta³em pi¹tkê i raz w mordê."
					+System.getProperty("line.separator")+" - A za co?"
					+System.getProperty("line.separator")+" - Pani pyta: Ile to jest 6 razy 4. No to powiedzia³em, ¿e 24. I dosta³em pi¹tkê. A póŸniej mnie zapyta³a ile to jest 4 razy 6."
					+System.getProperty("line.separator")+" - A dyæ to jeden ch*j - mówi ojciec."
					+System.getProperty("line.separator")+" - No w³aœnie, tak samo jej powiedzia³em !');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Nauczycielka pyta dzieci na lekcji:"
					+System.getProperty("line.separator")+" -Dzieci sk¹d siê bierze pr¹d?"
					+System.getProperty("line.separator")+" -Z burdelu odpowiada Jasiu."
					+System.getProperty("line.separator")+" -A, dlaczego?"
					+System.getProperty("line.separator")+" -Dzisiaj rano jak siê tato goli³ pr¹d znikn¹³, a on powiedzia³?:"
					+System.getProperty("line.separator")+" „Znowu te kur** pr¹d odciêli!”');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Przed lekcj¹ pani mówi, ¿e przyjedzie grupa Chiñczyków."
					+System.getProperty("line.separator")+"I mówi tak¿e do Jasia, aby tam siad³ z ty³u i nie zadawa³ g³upich pytañ."
					+System.getProperty("line.separator")+"Zaczê³a siê lekcja. Dzieci siê pytaj¹ jak siê ¿yje w Chinach...itp."
					+System.getProperty("line.separator")+"Nagle pewien Chiñczyk podchodzi do Jasia i pyta go:"
					+System.getProperty("line.separator")+"Dlaczego nie zadajesz ¿adnych pytañ ?"
					+System.getProperty("line.separator")+"Bo jak zadam jakieœ, to pan siê zdenerwuje."
					+System.getProperty("line.separator")+"Nie, na pewno nie, no dobra wal."
					+System.getProperty("line.separator")+"Z ilu jest pan jajek?"
					+System.getProperty("line.separator")+"No.... z czterech."
					+System.getProperty("line.separator")+"Wiedzia³em, bo z dwóch to nie by³by pan taki ¿ó³ty....');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Tato, mama mówi³a, ¿e ta szyba w naszych drzwiach wejœciowych jest niet³uk¹ca. "
					+System.getProperty("line.separator")+"- Tak, Jasiu. "
					+System.getProperty("line.separator")+"- Otó¿ muszê ci powiedzieæ, ¿e oboje bardzo siê myliliœcie.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Przychodzi Ma³gosia do babci z pytaniem:"
					+System.getProperty("line.separator")+"- Babciu skad siê bior¹ dzieci ?"
					+System.getProperty("line.separator")+"Babcia zak³opotana :"
					+System.getProperty("line.separator")+"- Ma³gosiu, Pan Bóg ma skrzyneczkê i kluczyk. Je¿eli rodzice chc¹ mieæ dzieci to Bóg otwiera skrzyneczkê tym kluczykiem i wychodz¹ dzieci."
					+System.getProperty("line.separator")+"Za jakiœ czas Ma³gosia zadowolona dzieli siê sekretem:"
					+System.getProperty("line.separator")+"- Babciu bêdê mia³a dzieci!"
					+System.getProperty("line.separator")+"Na to babcia oburzona :"
					+System.getProperty("line.separator")+"- Ma³gosiu tylko Pan Bóg ma kluczyk!"
					+System.getProperty("line.separator")+"- Ale Jaœ ma wytrych!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Dzieci mia³y przynieœæ do szko³y ro¿ne przedmioty zwi¹zane z medycyna. Ma³gosia przynios³a strzykawkê, Kasia banda¿, a Basia s³uchawki."
					+System.getProperty("line.separator")+" - A ty co przynios³eœ? - Pyta nauczycielka Jasia. "
					+System.getProperty("line.separator")+"- Aparat tlenowy! "
					+System.getProperty("line.separator")+"- Tak...? A sk¹d go wzi¹³eœ? "
					+System.getProperty("line.separator")+"- Od dziadka. "
					+System.getProperty("line.separator")+"- A co na to dziadek? "
					+System.getProperty("line.separator")+"- Eeech... cheee....');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani w szkole nakaza³a dzieciom napisaæ wypracowanie, które mia³o by siê koñczyæ zdaniem: Matka jest tylko jedna. Na drugi dzieñ pani pyta siê dzieci."
					+System.getProperty("line.separator")+" - No dzieci przeczytajcie co napisaliœcie - mo¿e zaczniemy od Ma³gosi. "
					+System.getProperty("line.separator")+"- Mama jest kochaj¹ca przytula nas, uk³ada do snu. Matka jest tylko jedna. "
					+System.getProperty("line.separator")+"- Gosiu 5 siadaj. Teraz Pawe³ek "
					+System.getProperty("line.separator")+"- Mama wyprowadza nas na spacery kupuje nam rowery. Matka jest tylko jedna. "
					+System.getProperty("line.separator")+"- Pawe³ku 5 siadaj. "
					+System.getProperty("line.separator")+"Wreszcie Pani przepyta³a prawie ca³¹ klasê. Pozosta³ tylko Jasiu. "
					+System.getProperty("line.separator")+"- Jasiu a ty, co napisa³eœ? "
					+System.getProperty("line.separator")+"- W domu balanga, a wódka leje siê litrami, goœcie rzygaj¹ na dywan. "
					+System.getProperty("line.separator")+"Nagle wódka siê skoñczy³a, wiêc matka do mnie: Jasiek skocz do lodówki przynieœ dwie wódki. Idê do kuchni, otwieram lodówkê i drê siê z kuchni: "
					+System.getProperty("line.separator")+"Matka! Jest tylko jedna!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Oko³o pó³nocy mama i tata Jasia odbywaj¹ stosunek. Mama strasznie skacze po tacie, wtedy wchodzi Jasiu po czym wybiega i zaczyna p³akaæ. Mama wybieg³a za nim. Zaskoczona mama mówi do Jasia:"
					+System.getProperty("line.separator")+" - Nie p³acz Jasiu, tatuœ jest gruby ja musia³am mu wcisn¹æ brzuch z powrotem ¿eby by³ chudy. "
					+System.getProperty("line.separator")+"- Nie da rady mamo! Jak Ty wychodzisz to przychodzi s¹siadka i zaczyna tatê nadmuchiwaæ.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani pyta siê dzieci na lekcji jakie znaj¹ czêœci cia³a cz³owieka:"
					+System.getProperty("line.separator")+"- Rêce."
					+System.getProperty("line.separator")+"- A do czego one potrzebne? - pyta nauczycielka"
					+System.getProperty("line.separator")+"- A bo mo¿na nimi zrobiæ jak¹kolwiek pracê."
					+System.getProperty("line.separator")+"- Co jeszcze?"
					+System.getProperty("line.separator")+"- Nogi, ¿eby na nich chodziæ."
					+System.getProperty("line.separator")+"Ale rêkê podnosi te¿ Jasio. Pani wie, ¿e mo¿e coœ g³upiego paln¹æ, ale ryzykuje."
					+System.getProperty("line.separator")+"- Skóra - mówi Jasio."
					+System.getProperty("line.separator")+"- Bardzo dobrze Jasiu, a do czego ona s³u¿y?"
					+System.getProperty("line.separator")+"- No jakby nie by³o skóry to by mê¿czyŸni nie mieli w czym jajec nosiæ.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Dzieci mia³y narysowaæ portret swojego taty. Pani podchodzi do Jasia i pyta "
					+System.getProperty("line.separator")+"-Czemu twój tata ma niebieskie w³osy "
					+System.getProperty("line.separator")+"-Bo niemia³em ³ysej kredki');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Ma³y Jasio przechodzi obok sypialni rodziców id¹c po szklankê wody. S³ysz¹c jêki i stêkania zajrza³ do rodziców i zasta³ rodziców uprawiaj¹cych .Zanim ojciec zd¹¿y³ zareagowaæ Jasiu zawo³a³:"
					+System.getProperty("line.separator") + "- O! Ja chcê na konika! Tato mogê Ci wskoczyæ na plecy?"
					+System.getProperty("line.separator") + "Ojciec zadowolony, ¿e nie bêdzie musia³ odpowiadaæ na inne pytania, zgodzi³ siê z widz¹c w tym mo¿liwoœæ dokoñczenia tego, co zacz¹³. Synek wskoczy³ mu na plecy i zaczê³a siê jazda. Wkrótce mama zaczê³a jêczeæ i stêkaæ coraz g³oœniej i szybciej. Jasio wo³a:"
					+System.getProperty("line.separator") + "- Tato, trzymaj siê teraz mocno ! W tym momencie ja i listonosz zawsze przyœpieszamy!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Po pla¿y nudystów spaceruje matka z synkiem. W pewnej chwili synek pyta: "
					+System.getProperty("line.separator") + "- Mamusiu, dlaczego jeden pan ma wiêkszego, a drugi mniejszego ptaszka? "
					+System.getProperty("line.separator") + "- Dlatego Jasiu, ¿e jeden jest biedny, a drugi bogaty. "
					+System.getProperty("line.separator") + "Jasio przytakuje ze zrozumieniem, a po chwili wo³a: "
					+System.getProperty("line.separator") + "- Popatrz mamusiu! Tamten pan w³aœnie siê bogaci!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Na lekcji geografii pani kaza³a dzieciom u³o¿yæ zdanie z wyrazem Himalaje"
					+System.getProperty("line.separator") + "Pierwszy zg³osi³ siê Jasio i mówi"
					+System.getProperty("line.separator") + "w górach Himalajach s³oñ powiesi³ siê na jajach"
					+System.getProperty("line.separator") + "Pani zbulwersowana wys³a³a ch³opaka po ojca."
					+System.getProperty("line.separator") + "Gdy przyszed³ pani kaza³a powtórzyæ Jasiowi wymyœlone zdanie."
					+System.getProperty("line.separator") + "Jasio powtórzy³ - w górach Himalajach s³oñ powiesi³ siê na .. tr¹bie"
					+System.getProperty("line.separator") + "Ojciec wzruszy³ ramionami i wyszed³, a Jasiu doda³ ... i jajami drzewa r¹bie');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Nauczycielka pyta dzieci ’kim chc¹ byæ jak bêd¹ doroœli?’"
					+System.getProperty("line.separator") + "Wstaje Jasio i mówi:"
					+System.getProperty("line.separator") + " - Ja chcê byæ sk*rwysynem."
					+System.getProperty("line.separator") + " - Ale co ty opowiadasz Jasiu?"
					+System.getProperty("line.separator") + " - No bo tata zawsze mówi - zobacz jak¹ wille ma ten sk*rwysyn! ..zobacz jaki samochód ma ten sk*rwysyn!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Ma³y Jasio wraca z lekcji religii. Mama pyta o czym siê uczyli."
					+System.getProperty("line.separator") + " - Dziœ pani opowiada³a nam o Moj¿eszu - odpowiada Jasio."
					+System.getProperty("line.separator") + " - To opowiedz, jak to by³o."
					+System.getProperty("line.separator") + " - Wiêc tak. Szli, szli, szli, doszli do Morza Czerwonego. Moj¿esz wyci¹gn¹³ telefon komórkowy, zadzwoni³ po ekipê budowlan¹, ekipa wybudowa³a most, przeszli na drug¹ stronê i poszli dalej."
					+System.getProperty("line.separator") + " - Jasiu! Pani wam to powiedzia³a?"
					+System.getProperty("line.separator") + " - Jakbym Ci powiedzia³ to tak, jak pani opowiada³a, nigdy byœ w to nie uwierzy³a!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Mama pyta Jasia: "
					+System.getProperty("line.separator") + " - Jaka jest ta wasza nowa pani od matematyki? "
					+System.getProperty("line.separator") + " - Bardzo fajna! Ju¿ drugi raz podczas tej zimy z³ama³a nogê!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W klasie pierwszej, prowadzonej przez bardzo seksown¹ i m³odziutk¹ nauczycielkê, w ostatniej ³awce, tu¿ za Jasiem, zasiad³ jaœnie pan dyrektor szko³y. Postanowi³ przeprowadziæ wizytacjê na lekcji najœwie¿szej w szkole nauczycielki. Pani, bardzo przejêta, odwróci³a do klasy swe apetyczne, opiête krótk¹ spódniczk¹ poœladki, pisz¹c na tablicy:"
					+System.getProperty("line.separator") + "- Ala ma kota. Nawrót i pytanie do klasy: - co ja napisa³am? Martwota i przera¿enie... Jedynie Jaœ wyrywa siê jak szalony. No...., no..., Jasiu? Pani, z ogromnym wahaniem, dobrze ju¿ znaj¹c wyskoki tego ³obuziaka, wezwa³a go do odpowiedzi."
					+System.getProperty("line.separator") + "- Ale ma dupê! - mówi Jaœ."
					+System.getProperty("line.separator") + "- Pa³a! - wybuch³a pani, czerwona na twarzy z oburzenia."
					+System.getProperty("line.separator") + "Jasio te¿ wœciek³y, siadaj¹c zwróci³ siê do ty³u, do dyrektora:"
					+System.getProperty("line.separator") + "- Jak nie umiesz czytaæ, to nie podpowiadaj.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W szkole u Jasia by³o malowanie. Pani nauczycielka w ramach zajêæ z plastyki chcia³a sprawdziæ czy dzieci maj¹ zmys³ plastyczny. Wiêc pyta siê dzieci jak by pomalowa³y klasê. Dzieci opowiadaj¹ swoje wizje klasy po malowaniu. Dosz³o do Jasia, wiêc mówi:"
					+System.getProperty("line.separator") + " - W zasadzie to podoba mi siê tak jak jest, tylko na œrodku œciany pierdoln¹³bym szlaczek !"
					+System.getProperty("line.separator") + " - Po tych s³owach nauczycielka z³apa³a siê za g³owê i mówi do Jasia:"
					+System.getProperty("line.separator") + " - Jasiu jutro widzê ciê tu z ojcem."
					+System.getProperty("line.separator") + "Na drugi dzieñ Jasiu przychodzi do szko³y z ojcem. Nauczycielka zrelacjonowa³a mu wiêc treœæ wczorajszej lekcji."
					+System.getProperty("line.separator") + " - I na koñcu mówi, ¿e na œrodku œciany pierdoln¹³by szlaczek..."
					+System.getProperty("line.separator") + " - Ojciec popatrzy³ na œcianê i mówi:"
					+System.getProperty("line.separator") + " - Ma pani racjê, rzeczywiœcie chujowo !');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jaœ z tat¹ s¹ w operze. Jaœ pyta:"
					+System.getProperty("line.separator") + "- Tato, czemu ten pan grozi tej pani kijem?"
					+System.getProperty("line.separator") + "- On jej nie grozi, on dyryguje."
					+System.getProperty("line.separator") + "- To czemu ta pani krzyczy?');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Nauczycielka do Jasia:"
					+System.getProperty("line.separator") + "- Przyznaj siê, œci¹ga³eœ od Ma³gosi!"
					+System.getProperty("line.separator") + "- Sk¹d pani to wie?"
					+System.getProperty("line.separator") + "- Bo obok ostatniego pytania ona napisa³a: nie wiem, a ty napisa³eœ: ja te¿.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasiek zakoñczy³ szko³ê œredni¹ z najlepszymi wynikami i jemu przypad³ zaszczyt przemówienia podczas rozdania œwiadectw. Stan¹³ na podeœcie i zaczyna:"
					+System.getProperty("line.separator") +"- Chcia³bym bardzo podziêkowaæ mojej kochanej mamie za ten wspania³y wp³yw jaki na mnie mia³a. Za to ¿e zawsze by³a przy mnie, ca³y czas mog³em na ni¹ liczyæ i ¿e pomaga³a mi kiedy tylko tej pomocy potrzebowa³em. Kocham j¹ nad ¿ycie i nie wiem kim bym by³ gdyby nie ona...."
					+System.getProperty("line.separator") +"Nagle Jasio stêkn¹³, zatrzyma³ siê i zacz¹³ z trudnoœci¹ literowaæ s³owo. Jednak przerwa³ na chwilkê i w koñcu przemówi³:"
					+System.getProperty("line.separator") +"- Przepraszam bardzo za przerwê.. ale pismo mojej mamy jest takie niewyraŸne...');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Przychodzi na lekcje spóŸniony Jaœ, a pani siê pyta:"
					+System.getProperty("line.separator")+"-Jasiu dlaczego siê spóŸni³eœ?"
					+System.getProperty("line.separator")+"-Bandyci mnie napadli!"
					+System.getProperty("line.separator")+"-O bo¿e! Nic ci siê nie sta³o?"
					+System.getProperty("line.separator")+"-Owszem. Zabrali mi zeszyt z wypracowaniem...');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W parku na ³awce siedzi Jasio, a obok niego siedzi kobieta w ci¹¿y. W pewnym momencie Jasio nie wytrzymuje i pyta kobietê:"
					+System.getProperty("line.separator")+"- Co tam pani ma?"
					+System.getProperty("line.separator")+"- Dzidziusia."
					+System.getProperty("line.separator")+"- A kocha je pani?"
					+System.getProperty("line.separator")+"- Oczywiœcie."
					+System.getProperty("line.separator")+"- To dlaczego je pani zjad³a?');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasio do dziadka :"
					+System.getProperty("line.separator")+"- Dziadku, masz jeszcze zêby ?"
					+System.getProperty("line.separator")+"- Nie."
					+System.getProperty("line.separator")+"- To potrzymaj mi kanapkê.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Gdy Jasiu jad³ œniadanie, nasz³o go takie pytanie:"
					+System.getProperty("line.separator")+"- Mamo, a dlaczego tata ma tak ma³o w³osów ?"
					+System.getProperty("line.separator")+"- Bo tatuœ du¿o myœli - odpowiedzia³a mama, zadowolona, ¿e uda³o jej siê wybrn¹æ z tak trudnego pytania."
					+System.getProperty("line.separator")+"Jasiu pomyœla³, podrapa³ siê w g³owê i zacz¹³ dalej wcinaæ kanapkê. Jednak coœ nie dawa³o mu spokoju. Po chwili znów zapyta³:"
					+System.getProperty("line.separator")+"- Mamo, to dlaczego Ty masz tak du¿o w³osów ?');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani katechetka na religii pyta dzieci czy mo¿na zobaczyæ matkê bosk¹?"
					+System.getProperty("line.separator")+"Jasiu siê zg³asza: "
					+System.getProperty("line.separator")+"- Mo¿na proszê pani ja widzia³em"
					+System.getProperty("line.separator")+"- Chyba zmyœlasz Jasiu. Gdzie widzia³eœ?"
					+System.getProperty("line.separator")+"- No dziœ rano wychodzi³a od ksiêdza i ksi¹dz mówi³ Matko Boska tylko ¿eby ciê nikt nie zobaczy³.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pewnego dnia nauczycielka pyta w szkole dzieci, co chcia³yby dostaæ, by spe³ni³o to ich marzenia. Zg³osi³a siê Kasia:"
					+System.getProperty("line.separator")+"- Ja chcia³abym mieæ z³oto, bo z³oto jest warte du¿o i mog³abym za niego sobie kupiæ Porsche!! "
					+System.getProperty("line.separator")+"Na to Kaziu: "
					+System.getProperty("line.separator")+"- A ja platynê, bo jest warta jeszcze wiêcej i móg³bym sobie za ni¹ kupiæ Ferrari!! "
					+System.getProperty("line.separator")+"Na to wyrwa³ siê Jasiu: "
					+System.getProperty("line.separator")+"- Proszê pani, ja to chcia³bym mieæ du¿o silikonu! Moja siostra ma raptem dwa takie worki silikonu! Gdyby pani widzia³a, jakie luksusowe bryki parkuj¹ pod naszym domem!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani pyta dzieci w szkole:"
					+System.getProperty("line.separator")+"- Co to jest rozdwojenie jaŸni?"
					+System.getProperty("line.separator")+"Zg³asza siê Jasio:"
					+System.getProperty("line.separator")+"- To jest tak, kiedy rodzice kochaj¹ siê przy w³¹czonym filmie porno - nie wiadomo gdzie patrzeæ!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Siedzi Jasiu na krawê¿niku i beczy. £zy mu ciurkiem lec¹ przez palce. Przechodzi jakiœ dziadek i pyta: - Synku, czemu p³aczesz??"
					+System.getProperty("line.separator")+"Jasiu na to - p³aczê, bo nie mogê robiæ tych rzeczy, które robi¹ starsi ch³opcy..."
					+System.getProperty("line.separator")+"Dziadek usiad³ obok niego i te¿ zacz¹³ p³akaæ');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani przedszkolanka widzi jak Jasiu rozrzuca zabawki, wiêc podchodzi do niego i pyta: -Jasiu, co robisz?"
					+System.getProperty("line.separator")+"-Bawiê siê "
					+System.getProperty("line.separator")+"-A w co ? "
					+System.getProperty("line.separator")+"-W kurwa maæ, gdzie s¹ kluczyki od samochodu');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W szkole. Trzecia klasa, lekcja matematyki."
					+System.getProperty("line.separator")+"- Aniu, co robi twój tata?"
					+System.getProperty("line.separator")+"- Jest nauczycielem w przedszkolu."
					+System.getProperty("line.separator")+"- A ile zarabia?"
					+System.getProperty("line.separator")+"- 760 z³."
					+System.getProperty("line.separator")+"- A mama?"
					+System.getProperty("line.separator")+"- Mama jest bibliotekark¹ i zarabia 640z³."
					+System.getProperty("line.separator")+"- To ile wynosi wasz bud¿et?"
					+System.getProperty("line.separator")+"- 1400 zl miesiêcznie."
					+System.getProperty("line.separator")+"- Bardzo dobrze, pi¹tka. Jasiu, a twój tata?"
					+System.getProperty("line.separator")+"- Mój tata jest celnikiem na przejœciu kolejowym i zarabia 900 z³, mama pracuje w izbie celnej na przejœciu samochodowym i zarabia 850 z³, nasz bud¿et wynosi 9000zl miesiêcznie."
					+System.getProperty("line.separator")+"- Eh, Jasiu, no Ÿle, znów bêdê ci musia³a jedynkê postawiæ..."
					+System.getProperty("line.separator")+"- Gówno mnie to obchodzi, przynajmniej ¿yjemy jak ludzie...');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasiu spóŸnia siê na lekcjê. Nauczycielka pyta go:"
					+System.getProperty("line.separator")+"- Dlaczego siê spóŸni³eœ?!"
					+System.getProperty("line.separator")+"Jasio odpowiada:"
					+System.getProperty("line.separator")+"- Proszê pani, my³em zêby, to ju¿ wiêcej siê nie powtórzy!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Dlaczego ci¹gle spóŸniasz siê do szko³y? - pyta nauczycielka Jasia."
					+System.getProperty("line.separator")+"- Bo nie mogê siê obudziæ na czas..."
					+System.getProperty("line.separator")+"- Nie masz budzika?"
					+System.getProperty("line.separator")+"- Mam, ale on dzwoni wtedy gdy œpiê.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W szkole na matematyce pani pyta Jasia:"
					+System.getProperty("line.separator")+"-Jasiu co to jest k¹t??"
					+System.getProperty("line.separator")+"Jasiu na to:"
					+System.getProperty("line.separator")+"-K¹t to najbrudniejsza czêœæ mojego pokoju');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Na lekcji biologii nauczycielka mówi: "
					+System.getProperty("line.separator")+"- Pamiêtajcie, dzieci, ¿e nie wolno ca³owaæ kotków ani piesków, bo od tego mog¹ siê przenosiæ ró¿ne groŸne zarazki. A mo¿e ktoœ z was ma na to przyk³ad? "
					+System.getProperty("line.separator")+"Zg³asza siê Jasio: "
					+System.getProperty("line.separator")+"- Ja mam, proszê pani. Moja ciocia ca³owa³a raz kotka."
					+System.getProperty("line.separator")+"- I co?"
					+System.getProperty("line.separator")+"- No i zdech³.');");
			break;
		case(1):
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','____');");
			break;
		case(2):
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','____');");
			break;
		// Line sep.==========================================================================
		//		"
		//		+System.getProperty("line.separator") + "
		// Used stuff=========================================================================
		// strony z http://www.kawalyojasiu.pl/ 7 8 9 13 14
		// TODO cudzys³owy? -> „Matko Boska tylko ¿eby ciê nikt nie zobaczy³”.
		}
	}

	private void initialize() {
		lk_lv_jokes_list = (ListView) findViewById(R.id.lk_lv_jokes_list);
		lk_lv_jokes_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				selected = (int) arg3;
				Bundle basket;
				basket = new Bundle();
				basket.putInt("the_id", getProperId(selected,where));
				Intent read_joke = new Intent(JokesList.this, ReadJoke.class);
				read_joke.putExtras(basket);
				startActivity(read_joke);
			}
		});
		lk_b_all = (Button)findViewById(R.id.lk_b_all);
		lk_b_new = (Button)findViewById(R.id.lk_b_new);
		lk_b_like = (Button)findViewById(R.id.lk_b_like);
		lk_b_all.setOnClickListener(this);
		lk_b_new.setOnClickListener(this);
		lk_b_like.setOnClickListener(this);
		lk_b_all.setBackgroundColor(Color.GREEN);
		lk_b_new.setBackgroundColor(Color.LTGRAY);
		lk_b_like.setBackgroundColor(Color.LTGRAY);
	}
	
	private int getProperId(int sel,int what) {
		Cursor cursor = database.rawQuery("SELECT * FROM jokes_list;", null);
		if (what==1)
			cursor = database.rawQuery("SELECT _id,content FROM jokes_list WHERE state=0;", null);
		else if (what==2)
			cursor = database.rawQuery("SELECT _id,content FROM jokes_list WHERE state=2;", null);
		int counter = 0;
		while (counter <= sel && counter <= cursor.getCount()) {
			cursor.moveToNext();
			counter++;
		}
		return cursor.getInt(0);
	}

	private void updateList(int what) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>(); // Mapa
		Cursor cursor = database.rawQuery("SELECT _id,content FROM jokes_list;", null);
		if (what==0)
			cursor = database.rawQuery("SELECT _id,content FROM jokes_list;", null);
		else if (what==1)
			cursor = database.rawQuery("SELECT _id,content FROM jokes_list WHERE state=0;", null);
		else
			cursor = database.rawQuery("SELECT _id,content FROM jokes_list WHERE state=2;", null);
		while (cursor.moveToNext()) {
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("title", cursor.getString(0));
			datum.put("content", cursor.getString(1));
			data.add(datum);
		}
		adapter = new SimpleAdapter(this, data, R.layout.joke_item, new String[] { "title", "content" }, new int[] { android.R.id.title, android.R.id.content });
		lk_lv_jokes_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.cb.startSession();
		this.cb.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		this.cb.onStop(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.cb.onDestroy(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.doubleBackToExitPressedOnce = false;
		updateList(where);
	}
	
	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			if (this.cb.onBackPressed())
				return;
			else
				super.onBackPressed();
			return;
		}
		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Kliknij ponownie wstecz aby zamkn¹æ.", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.lk_b_all):
			where=0;
			updateList(where);
			lk_b_all.setBackgroundColor(Color.GREEN);
			lk_b_new.setBackgroundColor(Color.LTGRAY);
			lk_b_like.setBackgroundColor(Color.LTGRAY);
			break;
		case(R.id.lk_b_new):
			where=1;
			updateList(where);
			lk_b_all.setBackgroundColor(Color.LTGRAY);
			lk_b_new.setBackgroundColor(Color.GREEN);
			lk_b_like.setBackgroundColor(Color.LTGRAY);
			break;
		case(R.id.lk_b_like):
			where=2;
			updateList(where);
			lk_b_all.setBackgroundColor(Color.LTGRAY);
			lk_b_new.setBackgroundColor(Color.LTGRAY);
			lk_b_like.setBackgroundColor(Color.GREEN);
			break;
		}
		
	}
}
