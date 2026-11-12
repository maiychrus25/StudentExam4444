package com.example.studentexam

import android.graphics.Rect
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StudentExamTest {
    @Test fun phoneErrorUpdatesWhileTypingAndSurvivesRotation() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            onView(withId(R.id.phoneInput)).perform(scrollTo(), replaceText("09a123"))
            closeSoftKeyboard()
            onView(withId(R.id.phoneError)).check(matches(withText(R.string.phone_invalid)))
            scenario.recreate()
            onView(withId(R.id.phoneError)).check(matches(withText(R.string.phone_invalid)))
            onView(withId(R.id.phoneInput)).perform(scrollTo(), replaceText("0912345678"))
            closeSoftKeyboard()
            onView(withId(R.id.phoneError)).check(matches(withText("")))
            onView(withId(R.id.phoneInput)).perform(scrollTo(), replaceText(""))
            closeSoftKeyboard()
            onView(withId(R.id.phoneError)).check(matches(withText("")))
        }
    }

    @Test fun eachBlankFieldAndMissingGenderBlocksSubmission() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            for (id in intArrayOf(R.id.fullNameInput, R.id.emailInput, R.id.phoneInput, R.id.majorInput)) {
                scenario.onActivity { activity ->
                    fillValid(activity)
                    activity.findViewById<EditText>(id).setText("   ")
                    activity.findViewById<View>(R.id.sendButton).performClick()
                    assertNotNull(activity.findViewById<EditText>(id).error)
                    assertEquals("", activity.fullNameValue)
                    assertEquals(View.VISIBLE, activity.findViewById<View>(R.id.phoneError).visibility)
                }
            }
            scenario.onActivity { activity ->
                fillValid(activity)
                activity.findViewById<RadioGroup>(R.id.genderGroup).clearCheck()
                activity.findViewById<View>(R.id.sendButton).performClick()
                assertEquals(View.VISIBLE, activity.findViewById<View>(R.id.genderError).visibility)
                assertEquals("", activity.genderValue)
            }
        }
    }

    @Test fun invalidPhoneCannotBeSubmittedEvenWhenOtherFieldsAreValid() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                fillValid(activity)
                for (invalid in listOf("+8490", "09a", "09 12")) {
                    activity.findViewById<EditText>(R.id.phoneInput).setText(invalid)
                    activity.findViewById<View>(R.id.sendButton).performClick()
                    assertNotNull(activity.findViewById<EditText>(R.id.phoneInput).error)
                    assertEquals("", activity.phoneValue)
                }
            }
        }
    }

    @Test fun submitTransfersFiveValuesAndPencilReturnsToEditableForm() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { fillValid(it) }
            onView(withId(R.id.sendButton)).perform(click())
            onView(withId(R.id.studentName)).check(matches(withText("Nguyễn Văn An")))
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                val card = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED).single() as StudentCardActivity
                assertEquals("an@example.com", card.intent.getStringExtra(StudentCardActivity.EXTRA_EMAIL))
                assertEquals("0912345678", card.intent.getStringExtra(StudentCardActivity.EXTRA_PHONE))
                assertEquals("Công nghệ thông tin", card.intent.getStringExtra(StudentCardActivity.EXTRA_MAJOR))
                assertEquals("Nữ", card.intent.getStringExtra(StudentCardActivity.EXTRA_GENDER))
                assertFits(card.findViewById(R.id.studentCard))
                assertFits(card.findViewById(R.id.editButton))
            }
            onView(withId(R.id.editButton)).perform(click())
            scenario.recreate()
            scenario.onActivity { activity ->
                assertEquals("Nguyễn Văn An", activity.fullNameValue)
                assertEquals("an@example.com", activity.emailValue)
                assertEquals("0912345678", activity.phoneValue)
                assertEquals("Công nghệ thông tin", activity.majorValue)
                assertEquals("Nữ", activity.genderValue)
                assertEquals(R.id.femaleOption, activity.findViewById<RadioGroup>(R.id.genderGroup).checkedRadioButtonId)
                activity.findViewById<EditText>(R.id.fullNameInput).setText("Trần Bình")
                activity.findViewById<RadioGroup>(R.id.genderGroup).check(R.id.maleOption)
            }
            onView(withId(R.id.sendButton)).perform(click())
            onView(withId(R.id.studentName)).check(matches(withText("Trần Bình")))
            pressBack()
            scenario.onActivity { assertEquals("Nam", it.genderValue) }
        }
    }

    @Test fun formFieldsAndActionsRemainAccessible() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            for (id in intArrayOf(R.id.fullNameInput, R.id.emailInput, R.id.phoneInput, R.id.majorInput, R.id.maleOption)) {
                onView(withId(id)).perform(scrollTo())
                scenario.onActivity { assertFits(it.findViewById(id)) }
            }
            scenario.onActivity { activity ->
                assertFits(activity.findViewById(R.id.sendButton))
                assertFits(activity.findViewById(R.id.phoneError))
                val title = activity.findViewById<TextView>(R.id.formTitle)
                assertTrue(title.layout.height <= title.height - title.totalPaddingTop - title.totalPaddingBottom)
            }
        }
    }

    private fun fillValid(activity: MainActivity) {
        activity.findViewById<EditText>(R.id.fullNameInput).setText("  Nguyễn Văn An  ")
        activity.findViewById<EditText>(R.id.emailInput).setText("  an@example.com  ")
        activity.findViewById<EditText>(R.id.phoneInput).setText("0912345678")
        activity.findViewById<EditText>(R.id.majorInput).setText("  Công nghệ thông tin  ")
        activity.findViewById<RadioGroup>(R.id.genderGroup).check(R.id.femaleOption)
    }

    private fun assertFits(view: View) {
        val rect = Rect()
        assertTrue(view.getGlobalVisibleRect(rect))
        assertEquals(view.width, rect.width())
        assertEquals(view.height, rect.height())
    }
}
