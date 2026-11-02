package io.github.warleysr.pronki.api.ankidroid

class AnkiDroidAPI {

    companion object {

        const val AGAIN = 1
        const val HARD = 2
        const val GOOD = 3
        const val EASY = 4

        private val NEXT_TIMES = listOf("1 min", "10 min", "1 day", "4 days")

        private val ERRANDS = listOf(
            "Where's the nearest pharmacy?" to "asking for help nearby",
            "I need to pick up my prescription." to "at the pharmacy counter",
            "I'm looking for whole wheat bread." to "finding items in the store",
            "Do you have this in a larger size?" to "shopping for clothes",
            "How much is a bag of apples?" to "checking prices at the market",
            "Can I pay with my credit card?" to "at the checkout",
            "What time do you close tonight?" to "asking about store hours"
        )

        private val FOOD = listOf(
            "Could I get a glass of water, please?" to "a polite request at a café",
            "A small coffee with milk, please." to "ordering a drink",
            "Can I see the menu, please?" to "sitting down to eat",
            "I'll have the soup and a salad." to "ordering your meal",
            "Could we get the check, please?" to "asking to pay",
            "Is this seat taken?" to "finding a place to sit",
            "I'd like a table for two, please." to "arriving at a restaurant"
        )

        private val HEALTH = listOf(
            "I'd like to make a doctor's appointment." to "calling the clinic",
            "I've had a headache since this morning." to "describing symptoms",
            "I'm feeling a little under the weather." to "saying you feel sick",
            "Do I need a prescription for this?" to "asking about medicine",
            "My throat's been sore for two days." to "describing symptoms",
            "Could you check my blood pressure, please?" to "at a check-up"
        )

        private val FAMILY = listOf(
            "Good morning, how are you today?" to "greeting someone warmly",
            "It's nice to see you again." to "meeting a friend",
            "The kids are doing great in school." to "sharing family news",
            "We're having dinner at my place tonight." to "inviting someone over",
            "Say hello to your family for me." to "sending regards",
            "I'll call you back in ten minutes." to "ending a phone call",
            "Have a safe trip home." to "saying goodbye"
        )

        private val DIRECTIONS = listOf(
            "Excuse me, how do I get to the station?" to "asking for directions",
            "Sorry, could you say that again?" to "asking someone to repeat",
            "Could you speak a little more slowly?" to "asking for slower speech",
            "Is it far from here?" to "checking the distance",
            "Turn left at the next corner, right?" to "confirming directions",
            "Which bus goes to the city center?" to "finding the right bus",
            "I'm running a few minutes late." to "letting someone know"
        )

        private val deckCards = linkedMapOf(
            DeckInfo(1L, "Errands & Shopping", 0, 0, ERRANDS.size) to ERRANDS,
            DeckInfo(2L, "Food & Cafés", 0, 0, FOOD.size) to FOOD,
            DeckInfo(3L, "Health & Appointments", 0, 0, HEALTH.size) to HEALTH,
            DeckInfo(4L, "Greetings & Family", 0, 0, FAMILY.size) to FAMILY,
            DeckInfo(5L, "Directions & Help", 0, 0, DIRECTIONS.size) to DIRECTIONS
        )

        private val decks = deckCards.keys.toList()
        private var currentDeck = decks.first()
        private var phraseIndex = 0

        fun isPermissionGranted(): Boolean = true

        fun getDeckList(): List<DeckInfo>? = decks

        fun selectDeck(deck: DeckInfo) {
            if (deck in decks) {
                currentDeck = deck
                phraseIndex = 0
            }
        }

        fun queryNextCard(amount: Int = 1): CardInfo? {
            val phrases = deckCards[currentDeck] ?: return null
            if (phrases.isEmpty()) return null
            val index = (phraseIndex + (amount - 1).coerceAtLeast(0)) % phrases.size
            val (phrase, hint) = phrases[index]
            return CardInfo(
                noteId = currentDeck.deckId * 1000 + index,
                cardOrd = 0,
                questionRaw = phrase,
                answerRaw = "$phrase<br><i>($hint)</i>",
                nextTimes = NEXT_TIMES
            )
        }

        fun reviewCard(card: CardInfo, ease: Int) {
            val phrases = deckCards[currentDeck] ?: return
            if (phrases.isNotEmpty())
                phraseIndex = (phraseIndex + 1) % phrases.size
        }
    }
}
