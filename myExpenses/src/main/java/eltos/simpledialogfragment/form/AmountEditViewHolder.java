package eltos.simpledialogfragment.form;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import org.totschnig.myexpenses.R;
import org.totschnig.myexpenses.ui.AmountEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

class AmountEditViewHolder extends FormElementViewHolder<AmountEdit> {
  @BindView(R.id.label)
  TextView label;
  @BindView(R.id.amount)
  AmountEditText amountEditText;

  protected AmountEditViewHolder(AmountEdit field) {
    super(field);
  }

  @Override
  protected int getContentViewLayout() {
    return R.layout.simpledialogfragment_form_item_amount;
  }

  @Override
  protected void setUpView(View view, Context context, Bundle savedInstanceState, SimpleFormDialog.DialogActions actions) {
    ButterKnife.bind(this, view);
    label.setText(field.getText(context));
    amountEditText.setFractionDigits(field.fractionDigits);
    amountEditText.setAmount(field.amount);
    // Positive button state for single element forms
    if (actions.isOnlyFocusableElement()) {
      amountEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
          actions.updatePosButtonState();
        }
      });
    }
  }

  @Override
  protected void saveState(Bundle outState) {

  }

  @Override
  protected void putResults(Bundle results, String key) {
    results.putSerializable(key, amountEditText.validate(false));
  }

  @Override
  protected boolean focus(SimpleFormDialog.FocusActions actions) {
    return false;
  }

  @Override
  protected boolean posButtonEnabled(Context context) {
    if (!field.required) return true;
    final Editable text = amountEditText.getText();
    return text != null && !TextUtils.isEmpty(text.toString());
  }

  @Override
  protected boolean validate(Context context) {
    return amountEditText.validate(true) != null;
  }
}
