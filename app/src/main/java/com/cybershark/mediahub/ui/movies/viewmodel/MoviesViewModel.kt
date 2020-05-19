package com.cybershark.mediahub.ui.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybershark.mediahub.data.models.MoviesModel

class MoviesViewModel : ViewModel() {

    private val _dummyMoviesData = MutableLiveData<List<MoviesModel>>().apply {
        value = listOf(
            MoviesModel("1", "Ip Man 4: The Finale", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcR2olhDVfWmAgGFRV93KBIQZ_bHu87GKJ9taowWZ6fSDPiKpUGt", 105, "2020", 3.55f, "Ip Man and his son encounter racial discrimination after traveling to the United States to seek a better life."),
            MoviesModel("2", "Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil, Mutant, Hellbound, Flesh-Eating Subhumanoid Zombified Living Dead, Part 3", "https://m.media-amazon.com/images/M/MV5BOGU2Yzk4NDgtMjM5Zi00ZDYzLWFkNTctZWFmNDlhNTBmMzIxXkEyXkFqcGdeQXVyMzU0NzkwMDg@._V1_QL50_.jpg", 80, "2005", null, null),
            MoviesModel("3", "Pacific Rim Uprising", "https://www.uphe.com/sites/default/files/styles/scale__344w_/public/PacificRimUprising_PosterArt.jpg?itok=cv-wSTX6", 110, "2018", 2.8f, "Jake Pentecost, who had left his Jaeger training midway, gets a chance to live up to his father's legacy as he teams up with his estranged sister and former co-pilot to stop a new Kaiju threat."),
            MoviesModel("4", "Richard Jewell", "https://upload.wikimedia.org/wikipedia/en/2/21/Richard_Jewell_Poster.jpg", 131, "2019", 3.75f, "American security guard Richard Jewell saves many lives from an exploding bomb at the 1996 Olympics, but is vilified."),
            MoviesModel("5", "I.T", "https://upload.wikimedia.org/wikipedia/en/5/5a/It_%282017%29_poster.jpg", 135, "2017", 3.65f, "Seven helpless and bullied children are forced to face their worst nightmares when Pennywise, a shape-shifting clown, reappears. The clown lives in the sewers and targets small innocent children."),
            MoviesModel("6", "CID Moosa", "https://upload.wikimedia.org/wikipedia/en/a/a2/C.I.D._Moosa.jpg", 160, "2003", 3.75f, "Moosa, a private detective, faces many challenges while solving various cases. His only rival is his own brother-in-law, Peethambaran, who is a police officer."),
            MoviesModel("7", "test", "", 0, "test", 0f, "test"),
            MoviesModel("8", "test", "", 0, "test", 0f, "test"),
            MoviesModel("9", "test", "", 0, "test", 0f, "test"),
            MoviesModel("10", "test", "", 0, "test", 0f, "test"),
            MoviesModel("11", "test", "", 0, "test", 0f, "test")
        )
    }
    val dummyMoviesData: LiveData<List<MoviesModel>> = _dummyMoviesData
}