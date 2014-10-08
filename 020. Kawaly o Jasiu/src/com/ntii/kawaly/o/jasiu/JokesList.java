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
		// Pe�ny ekran
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
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasio wr�ci� ze szko�y i ojciec pyta go jak posz�o."
					+System.getProperty("line.separator")+" - Z matematyki dosta�em pi�tk� i raz w mord�."
					+System.getProperty("line.separator")+" - A za co?"
					+System.getProperty("line.separator")+" - Pani pyta: Ile to jest 6 razy 4. No to powiedzia�em, �e 24. I dosta�em pi�tk�. A p�niej mnie zapyta�a ile to jest 4 razy 6."
					+System.getProperty("line.separator")+" - A dy� to jeden ch*j - m�wi ojciec."
					+System.getProperty("line.separator")+" - No w�a�nie, tak samo jej powiedzia�em !');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Nauczycielka pyta dzieci na lekcji:"
					+System.getProperty("line.separator")+" -Dzieci sk�d si� bierze pr�d?"
					+System.getProperty("line.separator")+" -Z burdelu odpowiada Jasiu."
					+System.getProperty("line.separator")+" -A, dlaczego?"
					+System.getProperty("line.separator")+" -Dzisiaj rano jak si� tato goli� pr�d znikn��, a on powiedzia�?:"
					+System.getProperty("line.separator")+" �Znowu te kur** pr�d odci�li!�');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Przed lekcj� pani m�wi, �e przyjedzie grupa Chi�czyk�w."
					+System.getProperty("line.separator")+"I m�wi tak�e do Jasia, aby tam siad� z ty�u i nie zadawa� g�upich pyta�."
					+System.getProperty("line.separator")+"Zacz�a si� lekcja. Dzieci si� pytaj� jak si� �yje w Chinach...itp."
					+System.getProperty("line.separator")+"Nagle pewien Chi�czyk podchodzi do Jasia i pyta go:"
					+System.getProperty("line.separator")+"Dlaczego nie zadajesz �adnych pyta� ?"
					+System.getProperty("line.separator")+"Bo jak zadam jakie�, to pan si� zdenerwuje."
					+System.getProperty("line.separator")+"Nie, na pewno nie, no dobra wal."
					+System.getProperty("line.separator")+"Z ilu jest pan jajek?"
					+System.getProperty("line.separator")+"No.... z czterech."
					+System.getProperty("line.separator")+"Wiedzia�em, bo z dw�ch to nie by�by pan taki ��ty....');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Tato, mama m�wi�a, �e ta szyba w naszych drzwiach wej�ciowych jest niet�uk�ca. "
					+System.getProperty("line.separator")+"- Tak, Jasiu. "
					+System.getProperty("line.separator")+"- Ot� musz� ci powiedzie�, �e oboje bardzo si� mylili�cie.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Przychodzi Ma�gosia do babci z pytaniem:"
					+System.getProperty("line.separator")+"- Babciu skad si� bior� dzieci ?"
					+System.getProperty("line.separator")+"Babcia zak�opotana :"
					+System.getProperty("line.separator")+"- Ma�gosiu, Pan B�g ma skrzyneczk� i kluczyk. Je�eli rodzice chc� mie� dzieci to B�g otwiera skrzyneczk� tym kluczykiem i wychodz� dzieci."
					+System.getProperty("line.separator")+"Za jaki� czas Ma�gosia zadowolona dzieli si� sekretem:"
					+System.getProperty("line.separator")+"- Babciu b�d� mia�a dzieci!"
					+System.getProperty("line.separator")+"Na to babcia oburzona :"
					+System.getProperty("line.separator")+"- Ma�gosiu tylko Pan B�g ma kluczyk!"
					+System.getProperty("line.separator")+"- Ale Ja� ma wytrych!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Dzieci mia�y przynie�� do szko�y ro�ne przedmioty zwi�zane z medycyna. Ma�gosia przynios�a strzykawk�, Kasia banda�, a Basia s�uchawki."
					+System.getProperty("line.separator")+" - A ty co przynios�e�? - Pyta nauczycielka Jasia. "
					+System.getProperty("line.separator")+"- Aparat tlenowy! "
					+System.getProperty("line.separator")+"- Tak...? A sk�d go wzi��e�? "
					+System.getProperty("line.separator")+"- Od dziadka. "
					+System.getProperty("line.separator")+"- A co na to dziadek? "
					+System.getProperty("line.separator")+"- Eeech... cheee....');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani w szkole nakaza�a dzieciom napisa� wypracowanie, kt�re mia�o by si� ko�czy� zdaniem: Matka jest tylko jedna. Na drugi dzie� pani pyta si� dzieci."
					+System.getProperty("line.separator")+" - No dzieci przeczytajcie co napisali�cie - mo�e zaczniemy od Ma�gosi. "
					+System.getProperty("line.separator")+"- Mama jest kochaj�ca przytula nas, uk�ada do snu. Matka jest tylko jedna. "
					+System.getProperty("line.separator")+"- Gosiu 5 siadaj. Teraz Pawe�ek "
					+System.getProperty("line.separator")+"- Mama wyprowadza nas na spacery kupuje nam rowery. Matka jest tylko jedna. "
					+System.getProperty("line.separator")+"- Pawe�ku 5 siadaj. "
					+System.getProperty("line.separator")+"Wreszcie Pani przepyta�a prawie ca�� klas�. Pozosta� tylko Jasiu. "
					+System.getProperty("line.separator")+"- Jasiu a ty, co napisa�e�? "
					+System.getProperty("line.separator")+"- W domu balanga, a w�dka leje si� litrami, go�cie rzygaj� na dywan. "
					+System.getProperty("line.separator")+"Nagle w�dka si� sko�czy�a, wi�c matka do mnie: Jasiek skocz do lod�wki przynie� dwie w�dki. Id� do kuchni, otwieram lod�wk� i dr� si� z kuchni: "
					+System.getProperty("line.separator")+"Matka! Jest tylko jedna!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Oko�o p�nocy mama i tata Jasia odbywaj� stosunek. Mama strasznie skacze po tacie, wtedy wchodzi Jasiu po czym wybiega i zaczyna p�aka�. Mama wybieg�a za nim. Zaskoczona mama m�wi do Jasia:"
					+System.getProperty("line.separator")+" - Nie p�acz Jasiu, tatu� jest gruby ja musia�am mu wcisn�� brzuch z powrotem �eby by� chudy. "
					+System.getProperty("line.separator")+"- Nie da rady mamo! Jak Ty wychodzisz to przychodzi s�siadka i zaczyna tat� nadmuchiwa�.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani pyta si� dzieci na lekcji jakie znaj� cz�ci cia�a cz�owieka:"
					+System.getProperty("line.separator")+"- R�ce."
					+System.getProperty("line.separator")+"- A do czego one potrzebne? - pyta nauczycielka"
					+System.getProperty("line.separator")+"- A bo mo�na nimi zrobi� jak�kolwiek prac�."
					+System.getProperty("line.separator")+"- Co jeszcze?"
					+System.getProperty("line.separator")+"- Nogi, �eby na nich chodzi�."
					+System.getProperty("line.separator")+"Ale r�k� podnosi te� Jasio. Pani wie, �e mo�e co� g�upiego paln��, ale ryzykuje."
					+System.getProperty("line.separator")+"- Sk�ra - m�wi Jasio."
					+System.getProperty("line.separator")+"- Bardzo dobrze Jasiu, a do czego ona s�u�y?"
					+System.getProperty("line.separator")+"- No jakby nie by�o sk�ry to by m�czy�ni nie mieli w czym jajec nosi�.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Dzieci mia�y narysowa� portret swojego taty. Pani podchodzi do Jasia i pyta "
					+System.getProperty("line.separator")+"-Czemu tw�j tata ma niebieskie w�osy "
					+System.getProperty("line.separator")+"-Bo niemia�em �ysej kredki');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Ma�y Jasio przechodzi obok sypialni rodzic�w id�c po szklank� wody. S�ysz�c j�ki i st�kania zajrza� do rodzic�w i zasta� rodzic�w uprawiaj�cych .Zanim ojciec zd��y� zareagowa� Jasiu zawo�a�:"
					+System.getProperty("line.separator") + "- O! Ja chc� na konika! Tato mog� Ci wskoczy� na plecy?"
					+System.getProperty("line.separator") + "Ojciec zadowolony, �e nie b�dzie musia� odpowiada� na inne pytania, zgodzi� si� z widz�c w tym mo�liwo�� doko�czenia tego, co zacz��. Synek wskoczy� mu na plecy i zacz�a si� jazda. Wkr�tce mama zacz�a j�cze� i st�ka� coraz g�o�niej i szybciej. Jasio wo�a:"
					+System.getProperty("line.separator") + "- Tato, trzymaj si� teraz mocno ! W tym momencie ja i listonosz zawsze przy�pieszamy!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Po pla�y nudyst�w spaceruje matka z synkiem. W pewnej chwili synek pyta: "
					+System.getProperty("line.separator") + "- Mamusiu, dlaczego jeden pan ma wi�kszego, a drugi mniejszego ptaszka? "
					+System.getProperty("line.separator") + "- Dlatego Jasiu, �e jeden jest biedny, a drugi bogaty. "
					+System.getProperty("line.separator") + "Jasio przytakuje ze zrozumieniem, a po chwili wo�a: "
					+System.getProperty("line.separator") + "- Popatrz mamusiu! Tamten pan w�a�nie si� bogaci!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Na lekcji geografii pani kaza�a dzieciom u�o�y� zdanie z wyrazem Himalaje"
					+System.getProperty("line.separator") + "Pierwszy zg�osi� si� Jasio i m�wi"
					+System.getProperty("line.separator") + "w g�rach Himalajach s�o� powiesi� si� na jajach"
					+System.getProperty("line.separator") + "Pani zbulwersowana wys�a�a ch�opaka po ojca."
					+System.getProperty("line.separator") + "Gdy przyszed� pani kaza�a powt�rzy� Jasiowi wymy�lone zdanie."
					+System.getProperty("line.separator") + "Jasio powt�rzy� - w g�rach Himalajach s�o� powiesi� si� na .. tr�bie"
					+System.getProperty("line.separator") + "Ojciec wzruszy� ramionami i wyszed�, a Jasiu doda� ... i jajami drzewa r�bie');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Nauczycielka pyta dzieci �kim chc� by� jak b�d� doro�li?�"
					+System.getProperty("line.separator") + "Wstaje Jasio i m�wi:"
					+System.getProperty("line.separator") + " - Ja chc� by� sk*rwysynem."
					+System.getProperty("line.separator") + " - Ale co ty opowiadasz Jasiu?"
					+System.getProperty("line.separator") + " - No bo tata zawsze m�wi - zobacz jak� wille ma ten sk*rwysyn! ..zobacz jaki samoch�d ma ten sk*rwysyn!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Ma�y Jasio wraca z lekcji religii. Mama pyta o czym si� uczyli."
					+System.getProperty("line.separator") + " - Dzi� pani opowiada�a nam o Moj�eszu - odpowiada Jasio."
					+System.getProperty("line.separator") + " - To opowiedz, jak to by�o."
					+System.getProperty("line.separator") + " - Wi�c tak. Szli, szli, szli, doszli do Morza Czerwonego. Moj�esz wyci�gn�� telefon kom�rkowy, zadzwoni� po ekip� budowlan�, ekipa wybudowa�a most, przeszli na drug� stron� i poszli dalej."
					+System.getProperty("line.separator") + " - Jasiu! Pani wam to powiedzia�a?"
					+System.getProperty("line.separator") + " - Jakbym Ci powiedzia� to tak, jak pani opowiada�a, nigdy by� w to nie uwierzy�a!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Mama pyta Jasia: "
					+System.getProperty("line.separator") + " - Jaka jest ta wasza nowa pani od matematyki? "
					+System.getProperty("line.separator") + " - Bardzo fajna! Ju� drugi raz podczas tej zimy z�ama�a nog�!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W klasie pierwszej, prowadzonej przez bardzo seksown� i m�odziutk� nauczycielk�, w ostatniej �awce, tu� za Jasiem, zasiad� ja�nie pan dyrektor szko�y. Postanowi� przeprowadzi� wizytacj� na lekcji naj�wie�szej w szkole nauczycielki. Pani, bardzo przej�ta, odwr�ci�a do klasy swe apetyczne, opi�te kr�tk� sp�dniczk� po�ladki, pisz�c na tablicy:"
					+System.getProperty("line.separator") + "- Ala ma kota. Nawr�t i pytanie do klasy: - co ja napisa�am? Martwota i przera�enie... Jedynie Ja� wyrywa si� jak szalony. No...., no..., Jasiu? Pani, z ogromnym wahaniem, dobrze ju� znaj�c wyskoki tego �obuziaka, wezwa�a go do odpowiedzi."
					+System.getProperty("line.separator") + "- Ale ma dup�! - m�wi Ja�."
					+System.getProperty("line.separator") + "- Pa�a! - wybuch�a pani, czerwona na twarzy z oburzenia."
					+System.getProperty("line.separator") + "Jasio te� w�ciek�y, siadaj�c zwr�ci� si� do ty�u, do dyrektora:"
					+System.getProperty("line.separator") + "- Jak nie umiesz czyta�, to nie podpowiadaj.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W szkole u Jasia by�o malowanie. Pani nauczycielka w ramach zaj�� z plastyki chcia�a sprawdzi� czy dzieci maj� zmys� plastyczny. Wi�c pyta si� dzieci jak by pomalowa�y klas�. Dzieci opowiadaj� swoje wizje klasy po malowaniu. Dosz�o do Jasia, wi�c m�wi:"
					+System.getProperty("line.separator") + " - W zasadzie to podoba mi si� tak jak jest, tylko na �rodku �ciany pierdoln��bym szlaczek !"
					+System.getProperty("line.separator") + " - Po tych s�owach nauczycielka z�apa�a si� za g�ow� i m�wi do Jasia:"
					+System.getProperty("line.separator") + " - Jasiu jutro widz� ci� tu z ojcem."
					+System.getProperty("line.separator") + "Na drugi dzie� Jasiu przychodzi do szko�y z ojcem. Nauczycielka zrelacjonowa�a mu wi�c tre�� wczorajszej lekcji."
					+System.getProperty("line.separator") + " - I na ko�cu m�wi, �e na �rodku �ciany pierdoln��by szlaczek..."
					+System.getProperty("line.separator") + " - Ojciec popatrzy� na �cian� i m�wi:"
					+System.getProperty("line.separator") + " - Ma pani racj�, rzeczywi�cie chujowo !');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Ja� z tat� s� w operze. Ja� pyta:"
					+System.getProperty("line.separator") + "- Tato, czemu ten pan grozi tej pani kijem?"
					+System.getProperty("line.separator") + "- On jej nie grozi, on dyryguje."
					+System.getProperty("line.separator") + "- To czemu ta pani krzyczy?');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Nauczycielka do Jasia:"
					+System.getProperty("line.separator") + "- Przyznaj si�, �ci�ga�e� od Ma�gosi!"
					+System.getProperty("line.separator") + "- Sk�d pani to wie?"
					+System.getProperty("line.separator") + "- Bo obok ostatniego pytania ona napisa�a: nie wiem, a ty napisa�e�: ja te�.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasiek zako�czy� szko�� �redni� z najlepszymi wynikami i jemu przypad� zaszczyt przem�wienia podczas rozdania �wiadectw. Stan�� na pode�cie i zaczyna:"
					+System.getProperty("line.separator") +"- Chcia�bym bardzo podzi�kowa� mojej kochanej mamie za ten wspania�y wp�yw jaki na mnie mia�a. Za to �e zawsze by�a przy mnie, ca�y czas mog�em na ni� liczy� i �e pomaga�a mi kiedy tylko tej pomocy potrzebowa�em. Kocham j� nad �ycie i nie wiem kim bym by� gdyby nie ona...."
					+System.getProperty("line.separator") +"Nagle Jasio st�kn��, zatrzyma� si� i zacz�� z trudno�ci� literowa� s�owo. Jednak przerwa� na chwilk� i w ko�cu przem�wi�:"
					+System.getProperty("line.separator") +"- Przepraszam bardzo za przerw�.. ale pismo mojej mamy jest takie niewyra�ne...');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Przychodzi na lekcje sp�niony Ja�, a pani si� pyta:"
					+System.getProperty("line.separator")+"-Jasiu dlaczego si� sp�ni�e�?"
					+System.getProperty("line.separator")+"-Bandyci mnie napadli!"
					+System.getProperty("line.separator")+"-O bo�e! Nic ci si� nie sta�o?"
					+System.getProperty("line.separator")+"-Owszem. Zabrali mi zeszyt z wypracowaniem...');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W parku na �awce siedzi Jasio, a obok niego siedzi kobieta w ci��y. W pewnym momencie Jasio nie wytrzymuje i pyta kobiet�:"
					+System.getProperty("line.separator")+"- Co tam pani ma?"
					+System.getProperty("line.separator")+"- Dzidziusia."
					+System.getProperty("line.separator")+"- A kocha je pani?"
					+System.getProperty("line.separator")+"- Oczywi�cie."
					+System.getProperty("line.separator")+"- To dlaczego je pani zjad�a?');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasio do dziadka :"
					+System.getProperty("line.separator")+"- Dziadku, masz jeszcze z�by ?"
					+System.getProperty("line.separator")+"- Nie."
					+System.getProperty("line.separator")+"- To potrzymaj mi kanapk�.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Gdy Jasiu jad� �niadanie, nasz�o go takie pytanie:"
					+System.getProperty("line.separator")+"- Mamo, a dlaczego tata ma tak ma�o w�os�w ?"
					+System.getProperty("line.separator")+"- Bo tatu� du�o my�li - odpowiedzia�a mama, zadowolona, �e uda�o jej si� wybrn�� z tak trudnego pytania."
					+System.getProperty("line.separator")+"Jasiu pomy�la�, podrapa� si� w g�ow� i zacz�� dalej wcina� kanapk�. Jednak co� nie dawa�o mu spokoju. Po chwili zn�w zapyta�:"
					+System.getProperty("line.separator")+"- Mamo, to dlaczego Ty masz tak du�o w�os�w ?');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani katechetka na religii pyta dzieci czy mo�na zobaczy� matk� bosk�?"
					+System.getProperty("line.separator")+"Jasiu si� zg�asza: "
					+System.getProperty("line.separator")+"- Mo�na prosz� pani ja widzia�em"
					+System.getProperty("line.separator")+"- Chyba zmy�lasz Jasiu. Gdzie widzia�e�?"
					+System.getProperty("line.separator")+"- No dzi� rano wychodzi�a od ksi�dza i ksi�dz m�wi� Matko Boska tylko �eby ci� nikt nie zobaczy�.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pewnego dnia nauczycielka pyta w szkole dzieci, co chcia�yby dosta�, by spe�ni�o to ich marzenia. Zg�osi�a si� Kasia:"
					+System.getProperty("line.separator")+"- Ja chcia�abym mie� z�oto, bo z�oto jest warte du�o i mog�abym za niego sobie kupi� Porsche!! "
					+System.getProperty("line.separator")+"Na to Kaziu: "
					+System.getProperty("line.separator")+"- A ja platyn�, bo jest warta jeszcze wi�cej i m�g�bym sobie za ni� kupi� Ferrari!! "
					+System.getProperty("line.separator")+"Na to wyrwa� si� Jasiu: "
					+System.getProperty("line.separator")+"- Prosz� pani, ja to chcia�bym mie� du�o silikonu! Moja siostra ma raptem dwa takie worki silikonu! Gdyby pani widzia�a, jakie luksusowe bryki parkuj� pod naszym domem!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani pyta dzieci w szkole:"
					+System.getProperty("line.separator")+"- Co to jest rozdwojenie ja�ni?"
					+System.getProperty("line.separator")+"Zg�asza si� Jasio:"
					+System.getProperty("line.separator")+"- To jest tak, kiedy rodzice kochaj� si� przy w��czonym filmie porno - nie wiadomo gdzie patrze�!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Siedzi Jasiu na kraw�niku i beczy. �zy mu ciurkiem lec� przez palce. Przechodzi jaki� dziadek i pyta: - Synku, czemu p�aczesz??"
					+System.getProperty("line.separator")+"Jasiu na to - p�acz�, bo nie mog� robi� tych rzeczy, kt�re robi� starsi ch�opcy..."
					+System.getProperty("line.separator")+"Dziadek usiad� obok niego i te� zacz�� p�aka�');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Pani przedszkolanka widzi jak Jasiu rozrzuca zabawki, wi�c podchodzi do niego i pyta: -Jasiu, co robisz?"
					+System.getProperty("line.separator")+"-Bawi� si� "
					+System.getProperty("line.separator")+"-A w co ? "
					+System.getProperty("line.separator")+"-W kurwa ma�, gdzie s� kluczyki od samochodu');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W szkole. Trzecia klasa, lekcja matematyki."
					+System.getProperty("line.separator")+"- Aniu, co robi tw�j tata?"
					+System.getProperty("line.separator")+"- Jest nauczycielem w przedszkolu."
					+System.getProperty("line.separator")+"- A ile zarabia?"
					+System.getProperty("line.separator")+"- 760 z�."
					+System.getProperty("line.separator")+"- A mama?"
					+System.getProperty("line.separator")+"- Mama jest bibliotekark� i zarabia 640z�."
					+System.getProperty("line.separator")+"- To ile wynosi wasz bud�et?"
					+System.getProperty("line.separator")+"- 1400 zl miesi�cznie."
					+System.getProperty("line.separator")+"- Bardzo dobrze, pi�tka. Jasiu, a tw�j tata?"
					+System.getProperty("line.separator")+"- M�j tata jest celnikiem na przej�ciu kolejowym i zarabia 900 z�, mama pracuje w izbie celnej na przej�ciu samochodowym i zarabia 850 z�, nasz bud�et wynosi 9000zl miesi�cznie."
					+System.getProperty("line.separator")+"- Eh, Jasiu, no �le, zn�w b�d� ci musia�a jedynk� postawi�..."
					+System.getProperty("line.separator")+"- G�wno mnie to obchodzi, przynajmniej �yjemy jak ludzie...');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Jasiu sp�nia si� na lekcj�. Nauczycielka pyta go:"
					+System.getProperty("line.separator")+"- Dlaczego si� sp�ni�e�?!"
					+System.getProperty("line.separator")+"Jasio odpowiada:"
					+System.getProperty("line.separator")+"- Prosz� pani, my�em z�by, to ju� wi�cej si� nie powt�rzy!');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Dlaczego ci�gle sp�niasz si� do szko�y? - pyta nauczycielka Jasia."
					+System.getProperty("line.separator")+"- Bo nie mog� si� obudzi� na czas..."
					+System.getProperty("line.separator")+"- Nie masz budzika?"
					+System.getProperty("line.separator")+"- Mam, ale on dzwoni wtedy gdy �pi�.');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','W szkole na matematyce pani pyta Jasia:"
					+System.getProperty("line.separator")+"-Jasiu co to jest k�t??"
					+System.getProperty("line.separator")+"Jasiu na to:"
					+System.getProperty("line.separator")+"-K�t to najbrudniejsza cz�� mojego pokoju');");
			database.execSQL("INSERT INTO jokes_list(state,content) VALUES ('0','Na lekcji biologii nauczycielka m�wi: "
					+System.getProperty("line.separator")+"- Pami�tajcie, dzieci, �e nie wolno ca�owa� kotk�w ani piesk�w, bo od tego mog� si� przenosi� r�ne gro�ne zarazki. A mo�e kto� z was ma na to przyk�ad? "
					+System.getProperty("line.separator")+"Zg�asza si� Jasio: "
					+System.getProperty("line.separator")+"- Ja mam, prosz� pani. Moja ciocia ca�owa�a raz kotka."
					+System.getProperty("line.separator")+"- I co?"
					+System.getProperty("line.separator")+"- No i zdech�.');");
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
		// TODO cudzys�owy? -> �Matko Boska tylko �eby ci� nikt nie zobaczy��.
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
		Toast.makeText(this, "Kliknij ponownie wstecz aby zamkn��.", Toast.LENGTH_SHORT).show();
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
