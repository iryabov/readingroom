package com.github.iryabov.readingroom

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.Resource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class ImageControllerTest constructor(
        @Autowired val mvc: MockMvc,
        @Value("classpath:cover.png") val testImage: Resource
) {
    private val expectedJson = """
        {
            "url": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAXkAAAH0CAYAAADYLV07AAAgAElEQVR4nO3dyXObeX7f8Q8AYt9IcCe4StxESd1Sd8+0p7fxOK7yuOJrLvY/4FROmYv/Bl/iU2pyT8WXnFNxynEOnna5q6entbRErdwJcQNJEMS+5kCCVm9qSiII8Mv369ItksDzIwm8+cOD5/k9jnq9Xpeker2u//G/n+rv/+Gp/tt/viEAwMXy13/3QH/562n91Z9Py+FwSJIc9Xq9vrWb03/623/Wf/mPMy0eIgDgbf3mt0/0X//mM/V3B+So1Wr1//A3/4fAA4Ahv/ntE/3Pv/0zOf77/3pc/+yap9XjAQCcsX9+VJLz7//haavHAQBogr//h6dyrCwu1ls9EABAczhbPQAAQPMQeQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwLCOVm7c4XC0cvMA8Ebq9Xqrh3BqzOQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMKyj1QPA68nn83r67Jlq9bo6XC4FQiG5OzrkcDjk9fnk7jj6lXa43XI6HKpWq6pWq6rV6yoUCqpVq6pUKsrlcqrVaiqXSvL7/RodGVEwGGzxdwfgrDlWFhfrLdu4w9GqTbetR48eqVQuK9bdrUAwKI/HI5fLJefxz8rhdMrv9yvg96vD7VZHR4eczqMXZE6n8+T/XS6XJKlarZ7cd61WU61WkyRVKhXVajWVSiXlcjkVi0XVjz9XrVZVrlR0mE5rN5lUT3e3hoeHz+1ngFfb39/X0vKy3G63ItGoQuGwPB7Ptx4jzlM8t2r1o6d+vVZTrV5XsVBQLpdTJpNRIZ+Xx+3WjRs3mvq9XFT1esuy+dqIfAtks1k9efpUwVBInZ2dCgSDJ1EOBAKKRCLy+Xzq6Og4+fh5qx7P+AuFgnZ3d1Uul1WtVpVOp7Wzva1YV5dGR0ZaMrbLYn19XcndXfX09ira2XnyWHC5XAoGgwqFQvJ4PGf2OGn8zhu/91wup1wu963P5/N5pfb3dXh4qMGBAQ0NDr71di8iIn/ajV+CyDeC3hWLqSsWk/v4CdnZ1aVAICCPxyO3293qYZ5auVxWLpfT7u6uSsWi0um0Xrx4obGREfX09LR6eBdWOp3W84UF9fb1KRqNqsPtltvtVnd3twKBQNs8RsrlsiqVijKZjA4PD1Uul1Wv1VSuVJQ+OFAymVR/b6/i8Xirh9pURP60GzcY+WQyqZ3dXfX198vn9crhdCoWiykSibR0Zt4s5XJZqVRKqf195fN5ra+vKxQIaGJiotVDa3tLS0vK5vOKx+Py+/0KhcOKxWLyer2tHtpra7wKyGQy2t/fV6VcVqlUUjKZ1GE6rfdu3271EM8UkT/txg1G/s7du/rZhx+qr6/PXNB/SrVaVS6X087Ojg5SKa2srGh8bEzdsVirh9Y2ni8sqFQua3h4WP5AQL29vQoEAiYfK9VqVaVSSalUSgeplGr1urKZjLa2tjR55cqFfqP/IkWeo2vOWL1el8/rNfmk/Skul0vhcFjhcFjValVD8bhSqZSWVlaUz2Y1NzfX6iG2xPbOjtbW1zU2NqaxiQn19/df6MCdlsvlkt/vl9/v1+DxvvtyuazNzU3d+cMfdPvWrRaP8HIg8mgKl8ul/v5+9ff3a3x8XBsbG1p/8UK7yaSuzc7K4/G0eohN9+z5c1WqVY2Nj+vjTz5RLBa7lH/8X+Z2uxUKBk+O7EHzEXk0ndfr1fj4uOLxuDZevNBOMqn1tTVdm5mR3+9v9fDO3Pz8vPzBoMavXLk0s3a0LyKPc+N2uzU6Nqb48LAGBga0srysg/19M8diN+I+MzenwcHBC/kGKuwh8jh3LpdL8XhcPT092tjY0IP5efm8Xk1evdrqob2R5eVlHRweau76dcXjceKOtkLk0TKN3Tjd3d1KrK/r6zt3dG129sLswtnf39fi8rJmZ2f1zu3bikajrR4S8D1EHi0XDoc1NT2tcCSi+YcPpXpdszMzrR7WKzV2zXz4R390cuQI0I6IPNpCYxdOZ2en1lZXdefuXV2fm2u7o3AaZ6bOXb+u4ZGRC/OqA5cXkUdbCQaDmpqeVjAU0tdffaXh4WH19fa2eliSjg6JLJXL+uDnPzd/2j7sIPJoOy6XSyMjI/J4PLp7544ODg40NTnZ0jHd/+Yb9Q8MaGZ2VuFwuKVjAV4HFw1B2+rv79dHH38sr8+nBw8ftmQMpVJJd+7e1fTMjN69dYvA48Ih8mhr4XBY773/vgYGB3Xv/v1z3XapVNLD+Xndun1bV65ebZuVIIHXQeTR9vx+v27cvKkrk5O6e+/euWzz5cCPjo1d+uUIcHEReVwIbrdbMzMzmpqebnrovxt44CIj8rgwXC6XpqanNX7lSlND/3B+Xu/cukXgYQKRx4Xicrk0Ozur7p4ezc/Pn/n93713T9fm5jQ+Pn7m9w20ApHHheN2u/XurVuqSVpdWzuz+713/77Gr1zRlQu6hg7wQ4g8LqRgMKgPfvYzJZNJlUqlt76/R48eqbOrS7Ozs7zJClOIPC6s3t5eXb95Uw/fcrfN7t6eCqWS3r11i8MkYQ6Rx4U2MTGhWHe3Hj1+/Mb3sbq6qvc/+IATnWASkceF5nK59O6tW8rl8290+28ePFB8ZIS1aGAWkceFFw6H5XjD25bK5Ut7gXFcDkQel5pDYrlgmEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBg2KW8xuvde/cUDIXk8XgkSbVaTfl8XvVaTddmZ+V08rcPgA2XKvLfPHigUDisjz75RJFI5GSdkmq1qlKppOfPn2v+8WNNXb0qr9fb4tECeJVMJqOnz57J5/fL6/NJ9bpy2awqlYpu37rV6uG1jUsT+Xv372viyhVdv3Hje59zuVzy+/26efOmxsfH9dXvf6+ReJzQA22oUqno/jffqLevT+/evq3e3l75fD5JR+Hf3NzUwvKyquWypqemWjza1rs0+yWuTE7+YOC/KxwO65NPP9XD+fkzWcIWwNlpXJrx+s2b+ujjjzU+Pq5gMCiXyyWXy6VoNKqZmRl9+umninV36+s7d1o95Ja7FJG//+DBa61P4na79cHPf65yudzEUQF4HbVaTQsLC/rFxx9rcnLylV/rcrl0+733NHf9+jmNrn1dish/9NFHr32beDyu7e1t1Wq1JowIwOsqFovq7utTd3f3qW8zNT3dxBFdDJci8pFo9I1ul85kmM0DbaJWq+kWb6i+tksR+Te9nNvExIQqlcoZjwbA66pUKnq+sMClGd/ApYj8m/IHAq0eAgAdHebcwaUZ3wiRB9D2arWagsFgq4dxIRF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPEzxer15sbLR6GEDbIfIwwR8IKJvNtnoYQNsh8gBgGJEHAMOIPAAYRuRxaeXzeTkcjlYPA2gqIo9La3d3V16fr9XDAJqKyOPSyhcKRB7mEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReZjQ4XKpWq22ehhA2yHyMCEUDqtYLLZ6GEDbIfIwwe/3q1artXoYQNsh8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXlcWrVaTU4u/wfjiDwurVKppGAo1OphAE1F5HFpVSoVBQOBVg8DaCoiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8THC73arX660eBtB2iDxM8Pv9rR4C0JaIPEzweDytHgLQlog8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwurXq9Lh9nysI4Io9LLRgMtnoIQFMReQAwjMgDgGFEHgAMI/IAYBiRhwkdHR2tHgLQlog8TPB4POK6UMD3EXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/K41DpcrlYPAWgqIo9Lqy5xjVeYR+QBwDAiDwCGEXmY4HTyUAZ+CM8MmNDR0SFHqwcBtCEiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRx6XGEsWwrqPVA8DZq1arkqRarfa9z303aq5LfI1Th46WKL5IGr/bSqWiUqmkQqGgbDarQj7/yts5nE55vV4Fg0H5fD51dHSoo6NDTqfzUj8GLoOL9Qi/xKrVqmq1mgqFgnK5nNIHB0omkydP+oZ6va56vX5ym+9yOJ1yOv5t5XWHwyHH8b8dDofC4bA6OzsVDIXk8/nk8Xjkdrub+J2dDWuhKpfLyuVy2tnZUTKZVKVcPvnd1up1VcpllUolVSoV1Wu1n/z+a7Wa6vW6HE6n3G63nE6nPB6PHA6HXC6XHA6HnE6notGoYt3dikQi8vl85n6ulxGRb0ONJ/j21tZJyGu1miqViorFokqlkhySrl65okgkcmbbLRaLWlxa0tLi4lEEvF55vd6TCPgDAfX19SkWi12Y+F8E5XJZqVRKm5ubOkilVK/XVa1WlctmVS6XNTY2pu6BgaaPI5vNaml5Wc+fPZPP75fX51OHyyWXy6Wenh719fcrEAjwe79giHwbyOfz2tne1ouNDVXK5ZPIVyoVTU9OKhQKncs4vF6vrs3O/ujnl5eX9YfVVfl8Pvn8fnW4XPJ6vRoYHFRfX5+8Xu+5jPOiq1aryuVySqyvK5lMqlQqKZPJyOt2a25urmXjCgaDunH9+vc+nslk9Gh+Xs+ePVMgEJDH41EoFNLElSsKBALM9tsckW+Bcrmszc1NJRIJVcpl5fN55bJZTV69eqYz87M2Pj6u8fHxb31sa2tLf/jqK4WOd+8Eg0ENDA4qFosR/ZcUi0Vtb29rc2NDhUJBmUxGtWpV79y82eqh/aRQKKTbt26d/LtUKml5ZUXr6+sKh8NyezwaGBhQPB5nlt+GiPw5yefzWlpa0kEqpUKhoIODA021edRPo7+/X/39/Sf/Xlld1ZdffKFwJCKv16uuWEwjIyOXcsZXrVa1vb2t9bU1ZTIZHRwcaHhoSOOjo60e2lvxeDyanpo6+Xcmk9G9O3e0vLwsn89H8NsMkW+iYrGohYUFHaRSymazKheLmpqaMj3DHRsd1dhLEXvw4IFWlpcViUTU19en4ZER+f3+Fo6w+bLZrNZWV7Wzs6O93V3Fh4Y0eeVKq4fVNKFQSO/dvi3p6HtvBD/g92tkdFR9fX2X7g98OyHyZ8zhcGh7Z0fPnj1TJpNRMZ/X9PS0PENDrR5aS9y4cePk/+/eu6fl5WVFo1ENDAyof2DAzB+8xqw9sb6u3d1dlUslvfvOOxd+1v66gsHgSfAzmYy+/OIL9fT2KhgK6dq1a2Z+3xcJkT9jsVhMj+bnNTM1pcGXdmNAuvXuu5KOnvx3vv5a0c5ORaNRDcXj6unpuZCzvXw+r/W1NW1ubp7M2ude8eb1ZdKY4ddqNa2tr+vz3/1OkUhE/f39cvz0zXFGiPwZ++7uCnzfyy/vX2xs6It//Vf1H+/HHRgYuBD7covFohKJhBYXFpTP5XTr3Xcv3az9tJxO58lzIp1O6/69ewqHwy0e1eVB5NFSQ4ODGhocVCaT0ddffaWuWExD8bji8Xhb7rsvl8tKJBJaXlrSwcGB3nvpqBP8tEgkonffeafVw7hUiPwFks/nlc1mlc3lVCqVjs5irNVUP/5840zXH+N46UxXp8Mhp8ulDpdLfr9foVBIgUCgZWu5vDy7v3vvnlZXVjQ0NKT48HBbzPrK5bI2XrzQ6uqqdpPJk7EC7Y7It5l0Oq3948MsG6eiS0cBLxQKcrpc6u7u1lA8rnAkolAodLL+isfjeeV9l0qlk//P5XI6ODhQan9fyWRS6y9eyOvxyOV0SsdLHTgkuTo6FAwG1X2Ox7039t3Pz89raWlJg0NDGhkZUXd397ls/2XValWbm5taWV7Wzva2bly/rrGRkXMfx6uUSiXt7++rWCodnUxXqZx8rv4D6xc1OI7/oDeWNHC73fL7/QoGAgoGg00fN84HkW+hZDKpdDqtUql0NBuv15UrFNTX16dr16+fLB9wVm9IvryvOxgMqre390e/tnHW7ebmppaXlrS5uSm/zyc5HHI6HPJ6verp6Wnq7L9x9uf6+rr+5fPPNT09rZHR0XML0P7enpaXl7WysqKZqSmNxOPnst3vKhaLSiaTyuXzqlWrqjVesR3/t1AsytXRoUgkolhvrzo7OyUdLb4WCAR+cBG2SqVyclZ1vVZTLpfT3t6eNjY3VSmX5ff7j94cbaxrJMnpcing96uzs/PczsLG2yPy56RSqWhzc1O5XO5khl4slXR1akpjY2NtN3Nyu92KRqOKRqOamZk5+Xi1WlUmk9GTx4/15OnTo0WsnE45XS5FwmF1dXWd+Yx/eHhYw8PDevDw4VFwZ2eberJNY7/7g2++UTQc1vvntGumUqnoIJ3WQSqlcrl8FPN6XflCQaFwWKOjo2e6fkw0Gj3V11WrVRUKBe0mk1pcWtKTp08V8PuPXvHp6NVeOBRST0/PhVvV8zLgN9JE+/v72t3bO1oxsFxWOBLRO7dvq7Oz80IcQfJDXC6XotGofv7hhycfK5fLSiaTun/vnjY2NxXw++V2u49WMwyHz2ym31hX5c7du0qsr2t8YkIDAwNneujl/t6eFhYWlEgkmv6majab1c7Ozsn7K+VKReVKRTMzMxoZHW2bVSBdLpeCwaCCwaBGx8a+9blisaiNjQ09fvRI2zs78rjdchy/0ovFYhf+jG4LiPwZSyQSymazqtZqyufzun7zpsbGxi5s1E/D7XZrcHBQg4ODko6e+EuLi3r8+LH8Xq86GrsSjnc/va3bt24pk8no919+qbGxMY2Ojb31/vrGG6v3799XNBxuSuBLpZL29vaUPjw8WaRseGRE79y+rVAo1BZBf11er/dbaxo1vq/nz57p2cKCAscL2QUCAfX39zPTbwF+4mdgc3NTh4eHqtZqcns8eu+DDxSJRi/kk/YseL1ezV67ptlr1yQdzY6//PJLbW5tKRAIKBIOv3XwQ6GQ3r99W4lEQv/y+eeaeItlA05m7+vrZ37UTCaTOZmt5wsFebxeffCzn6mzs9Pk48PlcikcDuv2e+/p9nvvqVqtKpVK6Zv79/Xo8WN53G653W719fWxX/+cEPk3lEwmlUqlVK1WVa3XTT9x31ZXLKY/+/WvJZ198OPHx9QnEgkNvMGa68+ePtXz58+PZu9nFPhG2MvlsjLZrGZmZzVx5cqlPKXfdXw02B//6leSjl4xLS4u6tHDhwoFgyevAtvxnAgriPxrqFQqWl9fP5mVffiLXygWixH21/BjwY9GIuo+viLRm4i/wZEvkUhE62trevcMlvvNZrNK7u6qWCwqk8loamZGU1NTpnfTvQm3262ZmRnNzMyoWq1qcWFB88fB93g8GhoaOpNdevg3RP4U0um0tnd2VCgUNDw6qrm5uXML+3cv+1csFJQvFJTP5195DPR3+f1+ebxehcPhb13js5URejn4z58/14NvvlE4FFI0ElFXV1fTn+yTV6++1e0bx6enDw+VTqc1Mjamn3344aWcsb8Jl8ulqelpTU1Pq1wu6+mTJ3r0+LGCgYDC4fC3lrDGmyPyr+B2u/XsyRPlCwV9/Mknrzyu/G29fE3PXDarWr2u+kuX/Hv5Ys3BYFCjr7lk79LSkg7SadVqNXm83qOrO/l88ni9cjoccjid8h8fAx2JRM70+PzTmJyc1OTkpLLZrD7/3e+0tb2taDSq7lis7fbdZjIZ7e7t6eDgQE6XS598+mlbnJUrHU0KKpXK0YW+i0WVyuWjs6Sz2R+85m+D0+GQ3++X1+uV1+eTx+1Wh9stn893Lhf7drvdun7jhq7fuHHyGDhIp+VxuzV0SVdwPSuOlcXFV58L38yNO85nLbqR71zN6LTK5XJTHuCNIxC2traUO37yFQoFpVIpOXQ0w2zFbPD5woLS6bQi0ajC4bA63G55PB719vYqcnwRkPNSrVb18MEDLS0uKtbV1fLD8Wq1mvb29pQ6OFA6ndaVyclzfUX3ssbjJZPJKJVKKX/8x79arapaqahUKqlQKKiQz6tarcrtdms4HldXV9eP3mc+n9fK6qpyuZzq0smrvFAo9G/X+T2+CHwgGFRXV5cikUhTXwlWq1U9efJEi8+fy+f1KhgO6xcfffTa97O2vHzmY/upJUTaCZE/J/l8XltbWzo8PFQhn1cymZRT0tUWBf20EomEtra3FevuPnrC+3zq7u5WZ2fnuY17cXFR9+/dU6yzU12xmDpPeRLPWWjsktnb21OpUtGnn3126pOIzkK1Wj059DK1v6/y8Qw9nU4rk8koGom89W6n11EsFrW4uKhCqaSenh75/X65XC55fT719PQ0LfzZbFa7yeT3jtM/DSJP5JuiWq0qfXCgFy9eqFypaH9vT9lMRrMzM20d9Z+STCa1sramnp4ehcJhBQOBoyf3ORwyurGxoS+/+EKd0aiinZ3q6uxs2pIKxWJR+/v72kkm5ero0Ge//OW5HAHSiPrO9rZSqZRKpZLS6bQO02lduXLlXP/AnValUlHixQvt7e+rt7dXweM3Ufv7+9viUGIiT+TPTOOU//W1NZVKJSUSCU2MjZk+629paUnpw0MNDA6qs6tLvb29TT+xZ39vT5//7ncKHZ9Kf5bhy+fz2tvb004yqXAkoo8/+aTpb06Xy2WlUintJpPK5/Pa399X5vDwWxfPvkhqtZp2dna0ubWlgcFBeX0++f1+jYyMtGSCQ+SJ/FtrXNMzn88rkUjo6sRE271ZeB6ePnumYqmkwcFBRTs71dvb29Q3JBOJhL768kv19PQo1tX1Vj/zxsz9xcaGBoeG9P4HHzT1D1W5XNbe3p52d3eVTqe1tbGh0ZER9fT0NG2brVKpVLSwuCjP8YXdg8Gg4vH4uQWfyF+CyA+NjDTlzdNkMqnd3V1tvHihaDis3t7elq3H3m7m5+clp1ODg4PqHxhQLBZr2oz4yZMnejQ/r4G+PsVisdfarVIqlZQ63q0Wjkb16aefNi3u3w375saGrrbpLphmKZVKev78uUKRiDo7OxUOhzU4NNTUV0tE/hJEvsPrPVlX5W0Vi0Wtrqwom8tpeWlJc7OznK33CsViUQ/m5xWPx9Xb13eyz7YZvvr975VYX9fE+PgrjySRjnYp7KdS2tjYUIfbrc9++cumzSwPDg60s7OjZDKpzY0NTV29anoX3mmVSiU9fvJEff39CofDTVtGmshfgsjfuXdP//4v/uKtZmj5fF4ry8va29tTvVbTcDzOrP01PXr0SHWHQyOjo+rv72/KMhCHh4f6f//0T5qenPzR3TeN1R8PDg/1qz/5k6aEpTFr397e1trqqkKBgCYmJs58O1ZkMhmtrq9rZHRUXV1d6uvrO7PHBpG/BJGXpNThoW6+wenrjbgnEgnFBwfbbt33iyidTuv54qImJiY0ODR05ktD3L93TyvLyydXmHpZsVjUw/l5TU5N6fqNG2e2zYbGH5Cd7W29SCR0fW7uQh9Ndd5KpZK+efhQ48fva53FCq5E/pJEfntnRz6/X5NTU6d60uXzea2urmp9bU0jbXpR6YuusStnZHRUw8PDZ7bf/u7du1pfXf3BC0Y/efpUXp9Pn3722Vtv52WHh4fa2trS0uKi6tWqrh2vwIk3U6vVtJ5InLxZ+6ax39jYUKVYPPPxEfnTbvwcI9+wvLqq3t5eRTs71dnZebI+isvlOlkWNZlMKrG+ruGhIeJ+Tr6+c0dD8bhGx8beKvbZbFb/9x//8Ud319y7f1/Do6O6dUaHJx4cHGh7a+vozcRA4GRddZyNWq2mFy9eyO31KtbdrZGRkVM9Nl5e/KwZF10n8qfdeAsi3/D1nTuKdnYqGo3K4/HI4XQqn89rfW1NN+bmWAmvRb6+c0fDo6MaHR197d04u7u7+sNXX8nldGp6auoHvyZ1cKCl5WX9uz/907fa9XZwcKDNzU09e/JE/X19b7TMMU7v5dhHo1ENxePf+/29vFxIPp9XqVBQX5PWmyLyp914CyOP9tXYjTM1NaWhoSF1xWKv/PrDw0Pt7u7q3p07pzor9NGjR3J7vXrv/fdfe4mCw8NDbW5s6Mnjx8S9Rfb397WxtaWR0dGTSUDjTOHNjQ319vQ0Le4NRP60GyfyeIXdvT2trq1pZnb2ZOv9z2sAAAPcSURBVIGyjo4O1Wo15XI5HaRSyhcKWlxYkM/rfa01XBKJhJK7u7r9/vvq6en5yfdpGmunPHz4kN0yIPKn3jiRxyns7u1pZWVFPb29CgQCKpZKSu7sKOD3a2py8q3u+5sHDxSORDQyOqpoJKJQOHyyz7ex2uPe3p6ePH6sWrWqa7OzZ/Et4YIj8qfdOJFHm2istjkUjyscDsvhdKpSLiuRSKherWpubq7VQ0QbIfKn3TiRB3ABXaTIc8omABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcCwjlZuvF6vt3LzAGAeM3kAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGEbkAcAwIg8AhhF5ADCMyAOAYUQeAAwj8gBgGJEHAMOIPAAYRuQBwDAiDwCGEXkAMIzIA4BhRB4ADCPyAGAYkQcAw4g8ABhG5AHAMCIPAIYReQAwjMgDgGFEHgAMI/IAYBiRBwDDiDwAGOb867970OoxAACa4K//7oGcf/nr6VaPAwDQBH/562k5/+rPp/Wb3z5p9VgAAGfoN799or/682n9f9U+6pliU2V3AAAAAElFTkSuQmCC",
            "id": "image.png"
        }
    """.trimIndent()

    @Test
    fun `upload image and convert to base64`() {
        mvc.perform(MockMvcRequestBuilders.multipart("/images/convert")
                .file(MockMultipartFile("file", "image.png", "text/plain", testImage.inputStream)))
                .andExpect(status().`is`(200))
                .andExpect(content().json(expectedJson, false))
    }

}