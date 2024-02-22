package com.dejvidleka.moviehub.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.getWatchProviders(providerName: String, context: Context) {

    val providers = getWatchProviders()
    val provider = providers[providerName]

    provider.let { provider ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(provider?.providerUrl))

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(provider?.providerUrl))
            context.startActivity(i)
        }

    }
}


fun getWatchProviders(): Map<String, WatchProvider> {
    return mapOf(
        "Apple TV" to WatchProvider("Apple TV", "https://tv.apple.com/"),
        "Showtime Apple TV Channel" to WatchProvider("Showtime Apple TV Channel", "https://tv.apple.com/"),
        "Apple TV Plus" to WatchProvider("Apple TV Plus", "https://tv.apple.com/"),
        "Google Play Movies" to WatchProvider("Google Play Movies", "https://play.google.com/store/movies"),
        "Vudu" to WatchProvider("Vudu", "https://www.vudu.com"),
        "Netflix" to WatchProvider("Netflix", "https://www.netflix.com"),
        "Amazon Prime Video" to WatchProvider("Amazon Prime Video", "https://www.primevideo.com"),
        "Amazon Video" to WatchProvider("Amazon Video", "https://www.amazon.com/Amazon-Video/b?node=2858778011"),
        "MUBI" to WatchProvider("MUBI", "https://mubi.com"),
        "Crackle" to WatchProvider("Crackle", "https://www.crackle.com"),
        "Hulu" to WatchProvider("Hulu", "https://www.hulu.com"),
        "NetMovies" to WatchProvider("NetMovies", "https://www.netflix.com"), // Placeholder, as NetMovies specific URL might vary
        "maxdome Store" to WatchProvider("maxdome Store", "https://store.maxdome.de"),
        "Stan" to WatchProvider("Stan", "https://www.stan.com.au"),
        "Quickflix Store" to WatchProvider("Quickflix Store", "https://www.quickflix.com.au"),
        "Fandor" to WatchProvider("Fandor", "https://www.fandor.com"),
        "Netzkino" to WatchProvider("Netzkino", "https://www.netzkino.de"),
        "Sky Go" to WatchProvider("Sky Go", "https://www.skygo.sky.de"),
        "WOW" to WatchProvider("WOW", "https://www.wowpresentsplus.com"), // Assuming WOW refers to WOW Presents Plus
        "Alleskino" to WatchProvider("Alleskino", "https://www.alleskino.de"),
        "MGM Plus" to WatchProvider("MGM Plus", "https://www.mgm.com"), // MGM Plus specific URL might differ
        "MGM Amazon ChannelPlus" to WatchProvider("MGM Amazon Channel", "https://www.mgm.com"), // MGM Plus specific URL might differ
        "Rakuten TV" to WatchProvider("Rakuten TV", "https://rakuten.tv"),
        "Showtime" to WatchProvider("Showtime", "https://www.sho.com"),
        "BBC iPlayer" to WatchProvider("BBC iPlayer", "https://www.bbc.co.uk/iplayer"),
        "Now TV" to WatchProvider("Now TV", "https://www.nowtv.com"),
        "Chili" to WatchProvider("Chili", "https://uk.chili.com"),
        "ITVX" to WatchProvider("ITVX", "https://www.itv.com"),
        "Starz" to WatchProvider("Starz", "https://www.starz.com"),
        "Looke" to WatchProvider("Looke", "https://www.looke.com.br"),
        "Volta" to WatchProvider("Volta", "https://www.volta.ie"),
        "BoxOffice" to WatchProvider("BoxOffice", "https://www.dstv.com/en-za/boxoffice"), // Regional service, URL for South Africa
        "ShowMax" to WatchProvider("ShowMax", "https://www.showmax.com"),
        "OCS Go" to WatchProvider("OCS Go", "https://www.ocs.fr"),
        "Canal VOD" to WatchProvider("Canal VOD", "https://www.canalplus.com/cinema/"),
        "Bbox VOD" to WatchProvider("Bbox VOD", "https://www.bouyguestelecom.fr/"),
        "Orange VOD" to WatchProvider("Orange VOD", "https://video-a-la-demande.orange.fr/"),
        "Atres Player" to WatchProvider("Atres Player", "https://www.atresplayer.com/"),
        "Filmin" to WatchProvider("Filmin", "https://www.filmin.es/"),
        "Filmin Plus" to WatchProvider("Filmin Plus", "https://www.filmin.es/"), // Filmin Plus is part of Filmin, using the same URL
        "Filmin Latino" to WatchProvider("Filmin Latino", "https://www.filminlatino.mx/"),
        "Microsoft Store" to WatchProvider("Microsoft Store", "https://www.microsoft.com/en-us/store/movies-and-tv"),
        "Pathé Thuis" to WatchProvider("Pathé Thuis", "https://www.pathe-thuis.nl/"),
        "Videoland" to WatchProvider("Videoland", "https://www.videoland.com/"),
        "Tubi TV" to WatchProvider("Tubi TV", "https://tubitv.com/"),
        "Viaplay" to WatchProvider("Viaplay", "https://viaplay.com/"),
        "CBS" to WatchProvider("CBS", "https://www.cbs.com/"),
        "AMC" to WatchProvider("AMC", "https://www.amc.com/"),
        "tenplay" to WatchProvider("tenplay", "https://10play.com.au/"),
        "The CW" to WatchProvider("The CW", "https://www.cwtv.com/"),
        "U-NEXT" to WatchProvider("U-NEXT", "https://video.unext.jp/"),
        "dTV" to WatchProvider("dTV", "https://pc.video.dmkt-sp.jp/"),
        "Acorn TV" to WatchProvider("Acorn TV", "https://acorn.tv/"),
        "Naver Store" to WatchProvider("Naver Store", "https://serieson.naver.com/movie/home.nhn"),
        "Watcha" to WatchProvider("Watcha", "https://watcha.com/"),
        "Shudder" to WatchProvider("Shudder", "https://www.shudder.com/"),
        "GuideDoc" to WatchProvider("GuideDoc", "https://guidedoc.tv/"),
        "Channel 4" to WatchProvider("Channel 4", "https://www.channel4.com/"),
        "Timvision" to WatchProvider("Timvision", "https://www.timvision.it/"),
        "Infinity+" to WatchProvider("Infinity+", "https://www.infinitytv.it/"),
        "Ivi" to WatchProvider("Ivi", "https://www.ivi.ru/"),
        "Okko" to WatchProvider("Okko", "https://okko.tv/"),
        "Amediateka" to WatchProvider("Amediateka", "https://www.amediateka.ru/"),
        "Kinopoisk" to WatchProvider("Kinopoisk", "https://www.kinopoisk.ru/"),
        "Amazon Prime Video" to WatchProvider("Amazon Prime Video", "https://www.primevideo.com/"),
        "Voot" to WatchProvider("Voot", "https://www.voot.com/"),
        "Hotstar" to WatchProvider("Hotstar", "https://www.hotstar.com/"),
        "FXNow" to WatchProvider("FXNow", "https://www.fxnetworks.com/fxnow"),
        "Bookmyshow" to WatchProvider("Bookmyshow", "https://in.bookmyshow.com/"),
        "Sky Store" to WatchProvider("Sky Store", "https://www.skystore.com/"),
        "SBS On Demand" to WatchProvider("SBS On Demand", "https://www.sbs.com.au/ondemand/"),
        "Videobuster" to WatchProvider("Videobuster", "https://www.videobuster.de/"),
        "Foxtel Now" to WatchProvider("Foxtel Now", "https://www.foxtel.com.au/now/index.html"),
        "ABC iview" to WatchProvider("ABC iview", "https://iview.abc.net.au/"),
        "FILMO" to WatchProvider("FILMO", "https://www.filmotv.fr/"),
        "Cineplex" to WatchProvider("Cineplex", "https://www.cineplex.com/"),
        "Sundance Now" to WatchProvider("Sundance Now", "https://www.sundancenow.com/"),
        "iciTouTV" to WatchProvider("iciTouTV", "https://ici.tou.tv/"),
        "Sixplay" to WatchProvider("Sixplay", "https://www.6play.fr/"),
        "ABC" to WatchProvider("ABC", "https://abc.com/"),
        "Movistar Plus" to WatchProvider("Movistar Plus", "https://ver.movistarplus.es/"),
        "blue TV" to WatchProvider("blue TV", "https://www.blue.ch/en/tv/"),
        "BritBox" to WatchProvider("BritBox", "https://www.britbox.com/"),
        "History" to WatchProvider("History", "https://www.history.com/"),
        "Lifetime" to WatchProvider("Lifetime", "https://www.mylifetime.com/"),
        "Viu" to WatchProvider("Viu", "https://www.viu.com/"),
        "Catchplay" to WatchProvider("Catchplay", "https://www.catchplay.com/"),
        "iflix" to WatchProvider("iflix", "https://www.iflix.com/"),
        "Hollystar" to WatchProvider("Hollystar", "https://www.hollystar.ch/"),
        "Claro video" to WatchProvider("Claro video", "https://www.clarovideo.com/"),
        "Watchbox" to WatchProvider("Watchbox", "https://www.watchbox.com/"),
        "Netflix Kids" to WatchProvider("Netflix Kids", "https://www.netflix.com/browse/genre/27346"),
        "Pantaflix" to WatchProvider("Pantaflix", "https://www.pantaflix.com/"),
        "MagentaTV" to WatchProvider("MagentaTV", "https://www.magentatv.de/"),
        "Hollywood Suite" to WatchProvider("Hollywood Suite", "https://hollywoodsuite.ca/"),
        "Universal Pictures" to WatchProvider("Universal Pictures", "https://www.uphe.com/"),
        "Screambox" to WatchProvider("Screambox", "https://www.screambox.com/"),
        "YouTube Premium" to WatchProvider("YouTube Premium", "https://www.youtube.com/premium"),
        "Curzon Home Cinema" to WatchProvider("Curzon Home Cinema", "https://www.curzonhomecinema.com/"),
        "Curiosity Stream" to WatchProvider("Curiosity Stream", "https://www.curiositystream.com/"),
        "Kanopy" to WatchProvider("Kanopy", "https://www.kanopy.com/"),
        "YouTube" to WatchProvider("YouTube", "https://www.youtube.com/"),
        "Starz Play Amazon Channel" to WatchProvider("Starz Play Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "AcornTV Amazon Channel" to WatchProvider("AcornTV Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "BritBox Amazon Channel" to WatchProvider("BritBox Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "Fandor Amazon Channel" to WatchProvider("Fandor Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "MUBI Amazon Channel" to WatchProvider("MUBI Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "Screambox Amazon Channel" to WatchProvider("Screambox Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "Showtime Amazon Channel" to WatchProvider("Showtime Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "Shudder Amazon Channel" to WatchProvider("Shudder Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "Sundance Now Amazon Channel" to WatchProvider("Sundance Now Amazon Channel", "https://www.amazon.com/gp/video/storefront/"),
        "The Roku Channel" to WatchProvider("The Roku Channel", "https://therokuchannel.roku.com/"),
        "PBS" to WatchProvider("PBS", "https://www.pbs.org/"),
        "Sky" to WatchProvider(providerName = "Sky", providerUrl = "https://www.sky.com/"),
        "Freeform" to WatchProvider(providerName = "Freeform", providerUrl = "https://www.freeform.com/"),
        "Hoopla" to WatchProvider(providerName = "Hoopla", providerUrl = "https://www.hoopladigital.com/"),
        "Eros Now" to WatchProvider(providerName = "Eros Now", providerUrl = "https://erosnow.com/"),
        "ARD Mediathek" to WatchProvider(providerName = "ARD Mediathek", providerUrl = "https://www.ardmediathek.de/ard/"),
        "Jio Cinema" to WatchProvider(providerName = "Jio Cinema", providerUrl = "https://www.jiocinema.com/"),
        "Rai Play" to WatchProvider(providerName = "Rai Play", providerUrl = "https://www.raiplay.it/"),
        "BFI Player" to WatchProvider(providerName = "BFI Player", providerUrl = "https://player.bfi.org.uk/"),
        "Telecine Play" to WatchProvider(providerName = "Telecine Play", providerUrl = "https://www.telecineplay.com.br/"),
        "Crave" to WatchProvider(providerName = "Crave", providerUrl = "https://www.crave.ca/"),
        "Zee5" to WatchProvider(providerName = "Zee5", providerUrl = "https://www.zee5.com/"),
        "Arte" to WatchProvider(providerName = "Arte", providerUrl = "https://www.arte.tv/"),
        "YouTube Free" to WatchProvider(providerName = "YouTube Free", providerUrl = "https://www.youtube.com/"), // Assuming free movies
        "France TV" to WatchProvider(providerName = "France TV", providerUrl = "https://www.france.tv/"),
        "Sony Liv" to WatchProvider(providerName = "Sony Liv", providerUrl = "https://www.sonyliv.com/"),
        "Universcine" to WatchProvider(providerName = "Universcine", providerUrl = "https://www.universcine.be/"), // Assuming Belgian website
        "Popcornflix" to WatchProvider(providerName = "Popcornflix", providerUrl = "https://www.popcornflix.com/"),
        "Meo" to WatchProvider(providerName = "Meo", providerUrl = "https://meo.pt/"), // Assuming Portuguese provider
        "Comedy Central" to WatchProvider(providerName = "Comedy Central", providerUrl = "https://www.cc.com/"),
        "VOD Poland" to WatchProvider(providerName = "VOD Poland", providerUrl = "https://vod.pl/"), // Assuming Polish website
        "7plus" to WatchProvider(providerName = "7plus", providerUrl = "https://7plus.com.au/"),
        "Boomerang" to WatchProvider(providerName = "Boomerang", providerUrl = "https://www.boomerang.com/"),
        "Horizon" to WatchProvider(providerName = "Horizon", providerUrl = "https://horizon.tv/"),  // Need to verify if there's a dedicated site
        "Urban Movie Channel" to WatchProvider(providerName = "Urban Movie Channel", providerUrl = "https://www.umc.tv/"),
        "Yupp TV" to WatchProvider(providerName = "Yupp TV", providerUrl = "https://www.yupptv.com/"),
        "fuboTV" to WatchProvider(providerName = "fuboTV", providerUrl = "https://www.fubo.tv/"),
        "Criterion Channel" to WatchProvider(providerName = "Criterion Channel", providerUrl = "https://www.criterionchannel.com/"),
        "Magnolia Selects" to WatchProvider(providerName = "Magnolia Selects", providerUrl = "https://www.magnoliaselects.com/"),
        "WWE Network" to WatchProvider(providerName = "WWE Network", providerUrl = "https://watch.wwe.com/"),
        "Noggin Amazon Channel" to WatchProvider(providerName = "Noggin Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "History Vault" to WatchProvider(providerName = "History Vault", providerUrl = "https://www.historyvault.com/"), // Part of A&E networks
        "Funimation Now" to WatchProvider(providerName = "Funimation Now", providerUrl = "https://www.funimation.com/"), // Merged with Crunchyroll
        "Neon TV" to WatchProvider(providerName = "Neon TV", providerUrl = "https://www.neontv.co.nz/"),
        "Boomerang Amazon Channel" to WatchProvider(providerName = "Boomerang Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "Cinemax Amazon Channel" to WatchProvider(providerName = "Cinemax Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "Hallmark Movies Now Amazon Channel" to WatchProvider(providerName = "Hallmark Movies Now Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "MZ Choice Amazon Channel" to WatchProvider(providerName = "MZ Choice Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "PBS Kids Amazon Channel" to WatchProvider(providerName = "PBS Kids Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "PBS Masterpiece Amazon Channel" to WatchProvider(providerName = "PBS Masterpiece Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "Viewster Amazon Channel" to WatchProvider(providerName = "Viewster Amazon Channel", providerUrl = "https://www.amazon.com/gp/video/storefront/?contentType=merch&ie=UTF8&node=2858778011"), // Needs specific Amazon link
        "Ziggo TV" to WatchProvider(providerName = "Ziggo TV", providerUrl = "https://www.ziggo.nl/televisie"), // Assuming Netherlands
        "RTL+" to WatchProvider(providerName = "RTL+", providerUrl = "https://www.rtlplus.com/"), // Assuming German RTL+
        "Pluto TV" to WatchProvider(providerName = "Pluto TV", providerUrl = "https://pluto.tv/"),
        "Joyn" to WatchProvider(providerName = "Joyn", providerUrl = "https://www.joyn.de/"),
        "Crave Starz" to WatchProvider(providerName = "Crave Starz", providerUrl = "https://www.crave.ca/"), // Assuming this is additional to the main Crave
        "Globoplay" to WatchProvider(providerName = "Globoplay", providerUrl = "https://globoplay.globo.com/"),
        "Cineplex" to WatchProvider("Cineplex", "https://www.cineplex.com/"),
        "Sundance Now" to WatchProvider("Sundance Now", "https://www.sundancenow.com/"),
        "iciTouTV" to WatchProvider("iciTouTV", "https://ici.tou.tv/"),
        "Sixplay" to WatchProvider("Sixplay", "https://www.6play.fr/"),
        "ABC" to WatchProvider("ABC", "https://abc.com/"),
        "Movistar Plus" to WatchProvider("Movistar Plus", "https://www.movistarplus.es/"),
        "blue TV" to WatchProvider("blue TV", "https://www.blue.ch/en/tv/"),
        "BritBox" to WatchProvider("BritBox", "https://www.britbox.com/"),
        "History" to WatchProvider("History", "https://www.history.com/"),
        "Lifetime" to WatchProvider("Lifetime", "https://www.mylifetime.com/"),
        "Viu" to WatchProvider("Viu", "https://www.viu.com/"),
        "Catchplay" to WatchProvider("Catchplay", "https://www.catchplay.com/"),
        "iflix" to WatchProvider("iflix", "https://www.iflix.com/"),
        "Hollystar" to WatchProvider("Hollystar", "https://www.hollystar.ch/"),
        "Claro video" to WatchProvider("Claro video", "https://www.clarovideo.com/"),
        "Watchbox" to WatchProvider("Watchbox", "https://www.watchbox.com/"),
        "Netflix Kids" to WatchProvider("Netflix Kids", "https://www.netflix.com/browse/genre/783"),
        "Pantaflix" to WatchProvider("Pantaflix", "https://www.pantaflix.com/"),
        "MagentaTV" to WatchProvider("MagentaTV", "https://www.magentatv.de/"),
        "Hollywood Suite" to WatchProvider("Hollywood Suite", "https://hollywoodsuite.ca/"),
        "Universal Pictures" to WatchProvider("Universal Pictures", "https://www.uphe.com/"),
        "Screambox" to WatchProvider("Screambox", "https://www.screambox.com/"),
        "YouTube Premium" to WatchProvider("YouTube Premium", "https://www.youtube.com/premium"),
        "Curzon Home Cinema" to WatchProvider("Curzon Home Cinema", "https://www.curzonhomecinema.com/"),
        "Curiosity Stream" to WatchProvider("Curiosity Stream", "https://www.curiositystream.com/"),
        "Kanopy" to WatchProvider("Kanopy", "https://www.kanopy.com/"),
        "YouTube" to WatchProvider("YouTube", "https://www.youtube.com/"),
        "Starz Play Amazon Channel" to WatchProvider("Starz Play Amazon Channel", "https://www.amazon.com/gp/video/storefront/ref=atv_app_amz_starz"),
        "AcornTV Amazon Channel" to WatchProvider("AcornTV Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "BritBox Amazon Channel" to WatchProvider("BritBox Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "Fandor Amazon Channel" to WatchProvider("Fandor Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "MUBI Amazon Channel" to WatchProvider("MUBI Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "Screambox Amazon Channel" to WatchProvider("Screambox Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "Showtime Amazon Channel" to WatchProvider("Showtime Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "Shudder Amazon Channel" to WatchProvider("Shudder Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "Sundance Now Amazon Channel" to WatchProvider("Sundance Now Amazon Channel", "https://www.amazon.com/Prime-Video/b?node=2858778011"),
        "The Roku Channel" to WatchProvider("The Roku Channel", "https://therokuchannel.roku.com/"),
        "PBS" to WatchProvider("PBS", "https://www.pbs.org/"),
        "NOW" to WatchProvider(providerName = "NOW", providerUrl = "https://www.nowtv.com/"),
        "Spectrum On Demand" to WatchProvider(providerName = "Spectrum On Demand", providerUrl = "https://www.spectrum.net/support/tv/spectrum-tv-app/"), // Assuming this is the relevant service
        "tvo" to WatchProvider(providerName = "tvo", providerUrl = "https://www.tvo.org/"),
        "Vidio" to WatchProvider(providerName = "Vidio", providerUrl = "https://www.vidio.com/"),
        "CINE" to WatchProvider(providerName = "CINE", providerUrl = "https://www.cine.ar/"), // Assuming Argentinian website
        "SVT" to WatchProvider(providerName = "SVT", providerUrl = "https://www.svtplay.se/"),
        "Cineasterna" to WatchProvider(providerName = "Cineasterna", providerUrl = "https://www.cineasterna.se/"),
        "Tele2 Play" to WatchProvider(providerName = "Tele2 Play", providerUrl = "https://www.tele2.se/tv"),
        "Oldflix" to WatchProvider(providerName = "Oldflix", providerUrl = "https://oldflix.com.br/"), // Assuming Brazilian website
        "Tata Play" to WatchProvider(providerName = "Tata Play", providerUrl = "https://www.tataplay.com/"), // Formerly Tata Sky
        "Hi-YAH" to WatchProvider(providerName = "Hi-YAH", providerUrl = "https://www.hiyah.com/"),
        "Player" to WatchProvider(providerName = "Player", providerUrl = "https://play.tvp.pl/"),
        "TBS" to WatchProvider(providerName = "TBS", providerUrl = "https://www.tbs.com/"),
        "tru TV" to WatchProvider(providerName = "tru TV", providerUrl = "https://www.trutv.com/"),
        "DisneyNOW" to WatchProvider(providerName = "DisneyNOW", providerUrl = "https://watch.disneynow.com/"),
        "Discovery+" to WatchProvider(providerName = "Discovery+", providerUrl = "https://www.discoveryplus.com/"),
        "IFFR Unleashed" to WatchProvider("IFFR Unleashed", "https://iffrunleashed.com/"),
        "IPLA" to WatchProvider("IPLA", "https://www.ipla.tv/"),
        "Tenk" to WatchProvider("Tenk", "https://www.tenk.fr/"),
        "Magellan TV" to WatchProvider("Magellan TV", "https://www.magellantv.com/"),
        "QFT Player" to WatchProvider("QFT Player", "https://queensfilmtheatre.com/"),
        "Telia Play" to WatchProvider("Telia Play", "https://www.telia.se/privat/tv/telia-play"),
        "BroadwayHD" to WatchProvider("BroadwayHD", "https://www.broadwayhd.com/"),
        "tvzavr" to WatchProvider("tvzavr", "https://www.tvzavr.ru/"),
        "More TV" to WatchProvider("More TV", "https://more.tv/"),
        "Cinépolis KLIC" to WatchProvider("Cinépolis KLIC", "https://www.cinepolisklic.com/"),
        "Filmzie" to WatchProvider("Filmzie", "https://www.filmzie.com/"),
        "Filmoteket" to WatchProvider("Filmoteket", "https://filmoteket.no/"),
        "Lionsgate Play" to WatchProvider("Lionsgate Play", "https://www.lionsgateplay.com/"),
        "MovieSaints" to WatchProvider("MovieSaints", "https://www.moviesaints.com/"),
        "KPN" to WatchProvider("KPN", "https://www.kpn.com/"),
        "Filme Filme" to WatchProvider("Filme Filme", "https://filmefilme.com.br/"),
        "True Story" to WatchProvider("True Story", "https://truestory.film/"),
        "DocAlliance Films" to WatchProvider("DocAlliance Films", "https://dafilms.com/"),
        "Premier" to WatchProvider("Premier", "https://www.premierplayer.tv/"),
        "RTL Play" to WatchProvider("RTL Play", "https://www.rtlplay.be/"),
        "KinoPop" to WatchProvider("KinoPop", "https://www.kinopop.com/"),
        "Oi Play" to WatchProvider("Oi Play", "https://oiplay.oi.com.br/"),
        "KoreaOnDemand" to WatchProvider("KoreaOnDemand", "https://www.koreaondemand.com/"),
        "Klik Film" to WatchProvider("Klik Film", "https://www.klikfilm.net/"),
        "TvIgle" to WatchProvider("TvIgle", "https://tvigle.ru/"),
        "Strim" to WatchProvider("Strim", "https://strim.no/"),
        "Nova Play" to WatchProvider("Nova Play", "https://novaplatforma.bg/"),
        "iQIYI" to WatchProvider("iQIYI", "https://www.iq.com/"),
        "Para" to WatchProvider("Para", "https://www.para.com/")

    )
}

data class WatchProvider(
    val providerName: String,
    val providerUrl: String

)